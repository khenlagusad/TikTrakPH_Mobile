
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Payment;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;

import java.util.Locale;

public class PaymentConfirmationViewModel extends ViewModel {

    public static class PaymentDetails {
        public final String referenceNumber;
        public final String amountDue;

        public PaymentDetails(String referenceNumber, String amountDue) {
            this.referenceNumber = referenceNumber;
            this.amountDue = amountDue;
        }
    }

    private final MutableLiveData<PaymentDetails> paymentDetails =
            new MutableLiveData<>();

    private final MutableLiveData<Boolean> paymentSuccess =
            new MutableLiveData<>();

    private final MutableLiveData<String> paymentError =
            new MutableLiveData<>();

    public LiveData<PaymentDetails> getPaymentDetails() {
        return paymentDetails;
    }

    public LiveData<Boolean> getPaymentSuccess() {
        return paymentSuccess;
    }

    public LiveData<String> getPaymentError() {
        return paymentError;
    }

    public void loadTicket(int ticketId) {

        Ticket ticket = RepositoryProvider
                .getTicketRepository()
                .getTicketById(ticketId);

        if (ticket == null) {
            paymentError.setValue("Ticket could not be found.");
            return;
        }

        TicketStatus status = RepositoryProvider
                .getTicketRepository()
                .getTicketStatus(ticketId);

        if (status != TicketStatus.UNPAID
                && status != TicketStatus.REJECTED) {
            paymentError.setValue(
                    "This ticket is not eligible for payment."
            );
            return;
        }

        String amount = String.format(
                Locale.getDefault(),
                "₱%,.2f",
                ticket.getAmountDue()
        );

        paymentDetails.setValue(
                new PaymentDetails(
                        ticket.getReferenceNumber(),
                        amount
                )
        );
    }

    public void confirmPayment(int ticketId) {

        paymentError.setValue(null);
        paymentSuccess.setValue(false);

        Payment payment = RepositoryProvider
                .getPaymentRepository()
                .createPayment(ticketId);

        if (payment == null) {
            paymentError.setValue(
                    "Payment could not be completed. "
                            + "The ticket may no longer be eligible for payment."
            );
            return;
        }

        paymentSuccess.setValue(true);
    }
}
