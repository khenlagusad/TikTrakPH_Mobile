
package com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile.data.model;

public class Dispute {

    public static final String PENDING = "pending";
    public static final String RESOLVED = "resolved";
    public static final String REJECTED = "rejected";

    private int id;
    private int ticketId;
    private int userId;
    private String title;
    private String explanation;
    private String status;
    private Integer adminId;
    private String adminNotes;
    private String submittedAt;
    private String reviewedAt;

    public Dispute(int id, int ticketId, int userId,
                   String title, String explanation, String status,
                   Integer adminId, String adminNotes,
                   String submittedAt, String reviewedAt) {

        this.id = id;
        this.ticketId = ticketId;
        this.userId = userId;
        this.title = title;
        this.explanation = explanation;
        this.status = status;
        this.adminId = adminId;
        this.adminNotes = adminNotes;
        this.submittedAt = submittedAt;
        this.reviewedAt = reviewedAt;
    }

    public int getId() {
        return id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getStatus() {
        return status;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public String getReviewedAt() {
        return reviewedAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }

    public void setReviewedAt(String reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}
