
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.tickets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.R;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.Ticket;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model.TicketStatus;
import com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.repository.RepositoryProvider;

import java.util.ArrayList;
import java.util.List;

public class ManagedTicketAdapter
        extends RecyclerView.Adapter<ManagedTicketAdapter.TicketViewHolder> {

    public interface OnTicketClickListener {
        void onTicketClick(Ticket ticket);
    }

    private final List<Ticket> tickets = new ArrayList<>();
    private final OnTicketClickListener listener;

    public ManagedTicketAdapter(OnTicketClickListener listener) {
        this.listener = listener;
    }

    public void setTickets(List<Ticket> newTickets) {
        tickets.clear();
        tickets.addAll(newTickets);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_managed_ticket, parent, false);

        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull TicketViewHolder holder,
            int position
    ) {
        Ticket ticket = tickets.get(position);

        TicketStatus status =
                RepositoryProvider.getTicketRepository()
                        .getTicketStatus(ticket.getId());

        holder.reference.setText(
                "Reference: " + ticket.getReferenceNumber()
        );

        holder.plate.setText(
                "Plate Number: " + ticket.getPlateNumber()
        );

        holder.amount.setText(
                "Amount Due: ₱" + ticket.getAmountDue().toPlainString()
        );

        holder.status.setText(formatStatus(status));

        int statusColor;

        switch (status) {
            case PAID:
            case RESOLVED:
                statusColor = holder.itemView.getContext()
                        .getColor(R.color.primary);
                break;

            case PENDING:
                statusColor = holder.itemView.getContext()
                        .getColor(R.color.text_secondary);
                break;

            case REJECTED:
            case UNPAID:
            default:
                statusColor = holder.itemView.getContext()
                        .getColor(R.color.danger);
                break;
        }

        holder.status.setTextColor(statusColor);

        holder.itemView.setOnClickListener(
                view -> listener.onTicketClick(ticket)
        );
    }

    private String formatStatus(TicketStatus status) {
        switch (status) {
            case UNPAID:
                return "Unpaid";
            case PAID:
                return "Paid";
            case PENDING:
                return "Pending";
            case REJECTED:
                return "Rejected";
            case RESOLVED:
                return "Resolved";
            default:
                return "Unknown";
        }
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {

        TextView reference;
        TextView plate;
        TextView amount;
        TextView status;

        TicketViewHolder(@NonNull View itemView) {
            super(itemView);

            reference = itemView.findViewById(R.id.tvTicketReference);
            plate = itemView.findViewById(R.id.tvTicketPlate);
            amount = itemView.findViewById(R.id.tvTicketAmount);
            status = itemView.findViewById(R.id.tvTicketStatus);
        }
    }
}
