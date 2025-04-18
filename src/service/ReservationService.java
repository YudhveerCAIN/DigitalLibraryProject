package service;

import dao.ReservationDAO;
import model.Reservation;

import java.util.List;

public class ReservationService {
    private ReservationDAO reservationDAO;

    public ReservationService() {
        reservationDAO = new ReservationDAO();
    }

    public boolean createReservation(Reservation reservation) {
        return reservationDAO.createReservation(reservation);
    }

    public Reservation getReservationById(int reservationId) {
        return reservationDAO.getReservationById(reservationId);
    }

    public boolean updateReservation(Reservation reservation) {
        return reservationDAO.updateReservation(reservation);
    }

    public boolean deleteReservation(int reservationId) {
        return reservationDAO.deleteReservation(reservationId);
    }

    public List<Reservation> getReservationsByUserId(int userId) {
        return reservationDAO.getReservationsByUserId(userId);
    }
}
