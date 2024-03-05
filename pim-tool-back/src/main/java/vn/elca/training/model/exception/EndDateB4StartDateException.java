package vn.elca.training.model.exception;

import java.time.LocalDate;

public class EndDateB4StartDateException extends Exception {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public EndDateB4StartDateException(LocalDate startDate, LocalDate endDate) {
        super(String.format("End date %s was set before start date %s", endDate, startDate));
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
