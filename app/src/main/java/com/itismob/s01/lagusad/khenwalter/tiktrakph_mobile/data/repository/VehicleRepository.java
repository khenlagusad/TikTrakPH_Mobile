
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Vehicle;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.source.MockData;

import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {

    public List<Vehicle> getAllVehicles() {
        return MockData.getVehicles();
    }

    public List<Vehicle> getVehiclesByUserId(int userId) {

        List<Vehicle> result = new ArrayList<>();

        for (Vehicle vehicle : MockData.getVehicles()) {

            if (vehicle.getUserId() == userId) {
                result.add(vehicle);
            }
        }

        return result;
    }

    public Vehicle getVehicleById(int vehicleId) {

        for (Vehicle vehicle : MockData.getVehicles()) {

            if (vehicle.getId() == vehicleId) {
                return vehicle;
            }
        }

        return null;
    }

    public Vehicle getVehicleByPlateNumber(String plateNumber) {

        for (Vehicle vehicle : MockData.getVehicles()) {

            if (vehicle.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                return vehicle;
            }
        }

        return null;
    }
}
