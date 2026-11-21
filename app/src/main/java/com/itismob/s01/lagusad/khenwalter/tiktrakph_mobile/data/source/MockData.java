
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.source;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.City;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Dispute;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Enforcer;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Payment;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.User;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Vehicle;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Violation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MockData {

    // Users
    private static final List<User> users = new ArrayList<>();

    // Vehicles
    private static final List<Vehicle> vehicles = new ArrayList<>();

    // Tickets
    private static final List<Ticket> tickets = new ArrayList<>();

    // Violations
    private static final List<Violation> violations = new ArrayList<>();

    // Enforcers
    private static final List<Enforcer> enforcers = new ArrayList<>();

    // Cities
    private static final List<City> cities = new ArrayList<>();

    // Payments
    private static final List<Payment> payments = new ArrayList<>();

    // Disputes
    private static final List<Dispute> disputes = new ArrayList<>();

    static {

        // -------------------------
        // USERS
        // -------------------------

        users.add(new User(
                1,
                "Khen",
                "Lagusad",
                "khen_walter",
                "demo123",
                "09687338385",
                "N23-12-123456",
                "2026-03-20 12:34:06"
        ));

        users.add(new User(
                4,
                "Jacob",
                "Yu",
                "jacob_yu",
                "demo123",
                "09236874590",
                "N12-34-567890",
                "2026-04-10 18:57:54"
        ));

        users.add(new User(
                5,
                "Keanan",
                "Olizon",
                "keanan_o",
                "demo123",
                "09236874595",
                "N12-34-566890",
                "2026-04-10 18:57:54"
        ));


        // -------------------------
        // VEHICLES
        // -------------------------

        vehicles.add(new Vehicle(
                1, 1, "ABC-1234",
                "Toyota", 2020, "Vios", "White"
        ));

        vehicles.add(new Vehicle(
                2, 1, "XYZ-5678",
                "Honda", 2019, "Civic", "Black"
        ));

        vehicles.add(new Vehicle(
                3, 1, "DEF-9012",
                "Mitsubishi", 2021, "Montero", "Silver"
        ));

        vehicles.add(new Vehicle(
                5, 4, "YUH-2345",
                "Lamborgihini", 2026, "Aventador", "Pink"
        ));


        // -------------------------
        // CITIES
        // -------------------------

        cities.add(new City(1, "Manila"));
        cities.add(new City(2, "Quezon City"));
        cities.add(new City(3, "Caloocan"));
        cities.add(new City(4, "Las Piñas"));
        cities.add(new City(5, "Makati"));
        cities.add(new City(6, "Malabon"));


        // -------------------------
        // ENFORCERS
        // -------------------------

        enforcers.add(new Enforcer(1, "Juan", "Santos", 1));
        enforcers.add(new Enforcer(2, "Maria", "Reyes", 2));
        enforcers.add(new Enforcer(3, "Jose", "Cruz", 3));
        enforcers.add(new Enforcer(4, "Ana", "Garcia", 4));
        enforcers.add(new Enforcer(5, "Pedro", "Bautista", 5));
        enforcers.add(new Enforcer(6, "Rosa", "Dizon", 6));


        // -------------------------
        // VIOLATIONS
        // -------------------------

        violations.add(new Violation(
                1,
                "Disregarding Traffic Signs",
                "Failure to follow posted traffic signs and signals",
                new BigDecimal("1000.00")
        ));

        violations.add(new Violation(
                2,
                "Illegal Parking (Attended)",
                "Vehicle illegally parked with driver present",
                new BigDecimal("1000.00")
        ));

        violations.add(new Violation(
                3,
                "Illegal Parking (Unattended)",
                "Vehicle illegally parked with no driver present",
                new BigDecimal("2000.00")
        ));

        violations.add(new Violation(
                5,
                "Truck Ban",
                "Trucks operating during banned hours",
                new BigDecimal("3000.00")
        ));

        violations.add(new Violation(
                6,
                "Light Truck Ban",
                "Light trucks operating during banned hours",
                new BigDecimal("2000.00")
        ));

        violations.add(new Violation(
                7,
                "Reckless Driving (1st Offense)",
                "Driving in a reckless manner endangering lives - 1st offense",
                new BigDecimal("1000.00")
        ));

        violations.add(new Violation(
                12,
                "Dress Code for Motorcycles (1st Offense)",
                "Non-compliance with motorcycle dress code - 1st offense",
                new BigDecimal("500.00")
        ));

        violations.add(new Violation(
                19,
                "Loading and Unloading in Prohibited Zones",
                "Picking up or dropping off passengers in restricted areas",
                new BigDecimal("1000.00")
        ));

        violations.add(new Violation(
                22,
                "Overspeeding",
                "Driving beyond the maximum speed limit",
                new BigDecimal("1000.00")
        ));


        // -------------------------
        // TICKETS
        // -------------------------

        tickets.add(new Ticket(
                1, "TIK-2024-000001",
                new BigDecimal("2000.00"),
                "ABC-1234", "N23-12-123456",
                3, 1, 1, "2024-11-01 08:30:00"
        ));

        tickets.add(new Ticket(
                2, "TIK-2024-000002",
                new BigDecimal("1000.00"),
                "XYZ-5678", "N23-12-123456",
                7, 2, 2, "2024-11-15 10:00:00"
        ));

        tickets.add(new Ticket(
                3, "TIK-2024-000003",
                new BigDecimal("500.00"),
                "DEF-9012", "N23-12-123456",
                12, 3, 3, "2024-12-01 14:45:00"
        ));

        tickets.add(new Ticket(
                4, "TIK-2024-000004",
                new BigDecimal("1000.00"),
                "ABC-1234", "N23-12-123456",
                19, 4, 4, "2025-01-10 09:15:00"
        ));

        tickets.add(new Ticket(
                5, "TIK-2024-000005",
                new BigDecimal("3000.00"),
                "XYZ-5678", "N23-12-123456",
                5, 5, 5, "2025-02-20 11:30:00"
        ));

        tickets.add(new Ticket(
                6, "TIK-2024-000006",
                new BigDecimal("1800.00"),
                "DEF-9012", "N23-12-123456",
                22, 6, 6, "2025-03-05 16:00:00"
        ));

        tickets.add(new Ticket(
                11, "TIK-2025-000007",
                new BigDecimal("1000.00"),
                "YUH-2345", "N12-34-567890",
                1, 2, 2, "2026-04-10 18:59:35"
        ));

        tickets.add(new Ticket(
                12, "TIK-2025-000008",
                new BigDecimal("1000.00"),
                "YUH-2345", "N12-34-567890",
                2, 4, 1, "2026-04-10 19:01:03"
        ));

        tickets.add(new Ticket(
                13, "TIK-2025-000009",
                new BigDecimal("2000.00"),
                "YUH-2345", "N12-34-567890",
                6, 5, 3, "2026-04-10 21:43:56"
        ));

        tickets.add(new Ticket(
                14, "TIK-2025-000010",
                new BigDecimal("1000.00"),
                "YUH-2345", "N12-34-567890",
                2, 4, 1, "2026-04-10 21:45:47"
        ));

        tickets.add(new Ticket(
                15, "TIK-2025-000011",
                new BigDecimal("1000.00"),
                "YUH-2345", "N12-34-567890",
                2, 2, 2, "2026-04-10 21:46:55"
        ));


        // -------------------------
        // PAYMENTS
        // -------------------------

        payments.add(new Payment(
                10, 1,
                new BigDecimal("2000.00"),
                "2026-04-09 13:33:59"
        ));

        payments.add(new Payment(
                11, 11,
                new BigDecimal("1000.00"),
                "2026-04-10 19:00:26"
        ));

        payments.add(new Payment(
                12, 13,
                new BigDecimal("2000.00"),
                "2026-04-10 21:45:32"
        ));

        payments.add(new Payment(
                13, 14,
                new BigDecimal("1000.00"),
                "2026-04-10 21:46:44"
        ));


        // -------------------------
        // DISPUTES
        // -------------------------

        disputes.add(new Dispute(
                9, 6, 1,
                "Hello", "World",
                Dispute.RESOLVED,
                1, "",
                "2026-04-09 10:51:09",
                "2026-04-09 13:25:48"
        ));

        disputes.add(new Dispute(
                10, 1, 1,
                "Camera Malfunction",
                "There was a camera malfunction on your part.",
                Dispute.REJECTED,
                1, "",
                "2026-04-09 13:26:54",
                "2026-04-09 13:29:13"
        ));

        disputes.add(new Dispute(
                11, 11, 4,
                "Was", "WAS",
                Dispute.REJECTED,
                1, "no",
                "2026-04-10 18:59:53",
                "2026-04-10 19:00:10"
        ));

        disputes.add(new Dispute(
                12, 12, 4,
                "nah thats cap", "capppppp",
                Dispute.RESOLVED,
                1, "",
                "2026-04-10 19:01:43",
                "2026-04-10 19:03:25"
        ));

        disputes.add(new Dispute(
                13, 4, 1,
                "no", "hell nawh",
                Dispute.REJECTED,
                1, "",
                "2026-04-10 19:11:48",
                "2026-04-10 19:12:11"
        ));

        disputes.add(new Dispute(
                14, 14, 4,
                "67", "67777",
                Dispute.REJECTED,
                1, "",
                "2026-04-10 21:46:01",
                "2026-04-10 21:47:42"
        ));

        disputes.add(new Dispute(
                15, 15, 4,
                "ASD", "ASD",
                Dispute.RESOLVED,
                1, "",
                "2026-04-10 21:47:04",
                "2026-04-10 21:47:10"
        ));

        disputes.add(new Dispute(
                16, 5, 1,
                "Incorrect Violation",
                "I believe this violation was incorrectly issued.",
                Dispute.PENDING,
                null, null,
                "2026-11-20 10:00:00",
                null
        ));

    }


    // -------------------------
    // GETTERS FOR SHARED DATA
    // -------------------------

    public static List<User> getUsers() {
        return users;
    }

    public static List<Vehicle> getVehicles() {
        return vehicles;
    }

    public static List<Ticket> getTickets() {
        return tickets;
    }

    public static List<Violation> getViolations() {
        return violations;
    }

    public static List<Enforcer> getEnforcers() {
        return enforcers;
    }

    public static List<City> getCities() {
        return cities;
    }

    public static List<Payment> getPayments() {
        return payments;
    }

    public static List<Dispute> getDisputes() {
        return disputes;
    }
}
