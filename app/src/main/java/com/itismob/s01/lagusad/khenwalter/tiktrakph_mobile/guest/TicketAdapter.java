
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.guest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Violation;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.TicketRepository;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.source.MockData;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.databinding.ItemTicketBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    public interface OnTicketClickListener {
        void onTicketClick(Ticket ticket);
    }

    private final List<Ticket> tickets;
    private final TicketRepository ticketRepository;
    private final OnTicketClickListener listener;

    public TicketAdapter(
            List<Ticket> tickets,
            TicketRepository ticketRepository,
            OnTicketClickListener listener) {

        this.tickets = new ArrayList<>(tickets);
        this.ticketRepository = ticketRepository;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        ItemTicketBinding binding = ItemTicketBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new TicketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull TicketViewHolder holder,
            int position) {

        holder.bind(tickets.get(position));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public void updateTickets(List<Ticket> newTickets) {
        tickets.clear();
        tickets.addAll(newTickets);
        notifyDataSetChanged();
    }

    private String getViolationName(int violationId) {
        for (Violation violation : MockData.getViolations()) {
            if (violation.getId() == violationId) {
                return violation.getName();
            }
        }

        return "Unknown Violation";
    }

    class TicketViewHolder extends RecyclerView.ViewHolder {

        private final ItemTicketBinding binding;

        TicketViewHolder(ItemTicketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Ticket ticket) {

            binding.tvReferenceNumber.setText(
                    ticket.getReferenceNumber()
            );

            binding.tvPlateNumber.setText(
                    ticket.getPlateNumber()
            );

            binding.tvViolation.setText(
                    getViolationName(ticket.getViolationId())
            );

            binding.tvAmount.setText(
                    String.format(
                            Locale.getDefault(),
                            "Amount Due: ₱%,.2f",
                            ticket.getAmountDue()
                    )
            );

            TicketStatus status = ticketRepository.getTicketStatus(
                    ticket.getId()
            );

            binding.tvStatus.setText(status.name());

            int statusColor;

            switch (status) {
                case PAID:
                case RESOLVED:
                    statusColor = R.color.primary;
                    break;

                case PENDING:
                    statusColor = R.color.text_secondary;
                    break;

                case REJECTED:
                case UNPAID:
                default:
                    statusColor = R.color.danger;
                    break;
            }

            binding.tvStatus.setTextColor(
                    binding.getRoot().getContext().getColor(statusColor)
            );

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTicketClick(ticket);
                }
            });
        }
    }
}
