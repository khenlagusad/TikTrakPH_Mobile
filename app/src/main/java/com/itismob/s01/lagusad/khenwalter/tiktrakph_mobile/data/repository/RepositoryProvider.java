
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository;

public class RepositoryProvider {

    private static final UserRepository userRepository =
            new UserRepository();

    private static final VehicleRepository vehicleRepository =
            new VehicleRepository();

    private static final TicketRepository ticketRepository =
            new TicketRepository();

    private static final PaymentRepository paymentRepository =
            new PaymentRepository(ticketRepository);

    private static final DisputeRepository disputeRepository =
            new DisputeRepository(ticketRepository, userRepository);

    private RepositoryProvider() {
        // Prevent creating additional provider instances.
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }

    public static TicketRepository getTicketRepository() {
        return ticketRepository;
    }

    public static PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }

    public static DisputeRepository getDisputeRepository() {
        return disputeRepository;
    }
}
