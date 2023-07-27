package com.ssi.devicemonitor.controller;

import com.ssi.devicemonitor.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DeviceMonitorController {
    @FXML
    private TextField txtHwName;
    @FXML
    private TextField txtHwManu;
    @FXML
    private TextField txtHwType;
    @FXML
    private TextField txtHwVersion;
    @FXML
    private TextField txtHwLocation;
    @FXML
    private TextField txtHwMac;


    @FXML
    private TextField txtSwName;
    @FXML
    private TextField txtSwManu;
    @FXML
    private TextField txtSwType;
    @FXML
    private TextField txtSwVersion;
    @FXML
    private TextField txtSwDate;

    @FXML
    private ComboBox cmbDeviceType;
    @FXML
    private ListView<Device> deviceListView;

    @FXML
    private Pane pnlHw;
    @FXML
    private Pane pnlSw;
    @FXML
    private TextField deviceNameTextField;

    @FXML
    private Button addDeviceButton;

    private DeviceMonitor deviceMonitor;

    private Device currentDevice;

    public void initialize() {
        deviceMonitor = new DeviceMonitor();

        deviceMonitor.addDevice(new GeneralDevice("Device 1"));
        deviceMonitor.addDevice(new GeneralDevice("Device 2"));
        deviceMonitor.addDevice(new GeneralDevice("Device 3"));

        deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
        deviceListView.setCellFactory(deviceListView -> new DeviceListCell());

        // Add context menu to ListView
        ContextMenu contextMenu = new ContextMenu();
        MenuItem removeItem = new MenuItem("Remove");
        MenuItem updateItem = new MenuItem("Update");

        removeItem.setOnAction(event -> {
            Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
            if (selectedDevice != null) {
                deviceMonitor.removeDevice(selectedDevice);
                deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
            }
        });

        updateItem.setOnAction(event -> {
            Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
            if (selectedDevice != null) {
                updateDevice(selectedDevice);
            }
        });

        contextMenu.getItems().addAll(removeItem, updateItem);
        deviceListView.setContextMenu(contextMenu);

        //Initialize CMB values
        cmbDeviceType.getItems().addAll(
                "HW Type",
                "SW Type"
        );

        cmbDeviceType.getSelectionModel().selectFirst();

        pnlHw.setVisible(false);
        pnlSw.setVisible(false);
    }

    private void updateDevice(Device selectedDevice) {
        clearFeilds();
        if (selectedDevice instanceof HwDevice)
        {
            currentDevice = selectedDevice;
            pnlHw.setVisible(true);
            pnlSw.setVisible(false);

            selectedDevice = (HwDevice) selectedDevice;

            txtHwName.setText(selectedDevice.getName());
            txtHwManu.setText(((HwDevice) selectedDevice).getManufacturer());
            txtHwLocation.setText(((HwDevice) selectedDevice).getLocation());
            txtHwType.setText(selectedDevice.getDeviceType());
            txtHwMac.setText(((HwDevice) selectedDevice).getMacAddress());
            txtHwVersion.setText(((HwDevice) selectedDevice).getVersion());
        }
        else if (selectedDevice instanceof SwDevice)
        {
            pnlHw.setVisible(false);
            pnlSw.setVisible(true);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String date = dateFormat.format(((SwDevice) selectedDevice).getDate());

            selectedDevice = (SwDevice) selectedDevice;

            txtSwName.setText(selectedDevice.getName());
            txtSwManu.setText(((SwDevice) selectedDevice).getManufacturer());
            txtSwType.setText(selectedDevice.getDeviceType());
            txtSwDate.setText(date);
            txtSwVersion.setText(((SwDevice) selectedDevice).getVersion());
        }
        else
        {
            pnlHw.setVisible(true);
            pnlSw.setVisible(false);
            txtHwName.setText(selectedDevice.getName());
        }

    }

    private void clearFeilds() {
        txtHwName.setText("");
        txtHwManu.setText("");
        txtHwLocation.setText("");
        txtHwType.setText("");
        txtHwMac.setText("");
        txtHwVersion.setText("");
        txtSwDate.setText("");
        txtSwManu.setText("");
        txtSwName.setText("");
        txtSwVersion.setText("");
        txtSwType.setText("");
    }

    private class DataUpdateTask extends TimerTask {
        private Random random = new Random();

        @Override
        public void run() {
            refreshListView();
        }
    }

    @FXML
    private void onHwBtnClose(){
        pnlHw.setVisible(false);
    }

    @FXML
    private void onHwBtnUpdateClick()
    {

        String s = txtHwManu.getText();

        //deviceMonitor.getDevices().contains();
        currentDevice = (HwDevice) currentDevice;

        // for each loop
        for (Device device : deviceMonitor.getDevices())
        {
            if (device.getName().equals(currentDevice.getName()))
            {
                ((HwDevice) device).setLocation(txtHwLocation.getText());
                ((HwDevice) device).setDeviceType(txtHwType.getText());
                ((HwDevice) device).setManufacturer(txtHwManu.getText());
                ((HwDevice) device).setVersion(txtHwVersion.getText());
                ((HwDevice) device).setMacAddress(txtHwMac.getText());

                deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
                break;
            }
        }

        pnlHw.setVisible(false);
    }

    @FXML
    //Open file explorer and choose file to load
    private void openFile(){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));
        File f = fc.showOpenDialog(null);

        if (f != null){
            //txtFile.setText(f.getAbsolutePath());
            Scanner myReader = null;
            try {
                myReader = new Scanner(f);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

            }
            myReader.close();
        }

    }
    @FXML
    private void addDevice() {
        String deviceName = deviceNameTextField.getText();
        Device newDevice;
        if (cmbDeviceType.getValue() == "HW Type")
        {
            newDevice = new HwDevice(deviceName, "Sonny", "Hw", "3", "Israel", "aa:bb:cc" );
        }
        else //SW Type
        {
            newDevice = new SwDevice(deviceName, "Sonny", "Sw", "2", new Date());
        }

        deviceMonitor.addDevice(newDevice);
        deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
        deviceNameTextField.clear();
    }



    public void refreshListView() {
        deviceListView.refresh();
    }

    private class DeviceListCell extends ListCell<Device> {
        @Override
        protected void updateItem(Device device, boolean empty) {
            super.updateItem(device, empty);

            if (device == null || empty) {
                setText(null);
                setGraphic(null);
                setStyle(""); // Reset the cell style
            } else {
                setText(device.getName() + " - " + device.getStatus());

                // Set the cell style based on the device status
                if (device.getStatus().equals("Online")) {
                    setStyle("-fx-text-fill: green;");
                } else if (device.getStatus().equals("Offline")) {
                    setStyle("-fx-text-fill: red;");
                } else {
                    setStyle(""); // Reset the cell style
                }
            }
        }
    }
}
