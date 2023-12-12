package tp.avigliano

import java.time.LocalDateTime
import java.time.Duration
import java.time.temporal.ChronoUnit
import grails.gorm.annotation.Entity

enum EnrollmentStatus {
    REQUESTED,
    REJECTED,
    APPROVED,
    CONFIRMED
}

@Entity
class Enrollment {

    // static enrolledTo = [league:League]
    League enrolledTo
    String teamName
    EnrollmentStatus status = EnrollmentStatus.REQUESTED
    Integer timesRejected = 0
    LocalDateTime rejectionDatetime

    def beforeInsert() {
        if(!enrolledTo.hasRoomForEnrollments()) {
            throw new TooManyEnrollmentsException("There are too many enrollments being processed right now. Please try again later")
        }
    }

    def beforeUpdate() {
        if(status == EnrollmentStatus.REJECTED) {
            def secondsDifference = ChronoUnit.SECONDS.between(rejectionDatetime, LocalDateTime.now())
            if(secondsDifference > enrolledTo.correctionTimeWindow) {
                throw new DeadEnrollmentException("This enrollment has been rejected for $secondsDifference days. It is too late to correct it now and it can not be processed further")
            }
        } else if(timesRejected >= 3) {
            throw new DeadEnrollmentException("This enrollment has been rejected too many times. It can not be processed further")
        }
    }

    String toString() {
        return "$teamName"
    }

    static class DeadEnrollmentException extends Exception {
         DeadEnrollmentException(String errorMessage) {
            super(errorMessage);
        }
    }

    static class TooManyEnrollmentsException extends Exception {
         TooManyEnrollmentsException(String errorMessage) {
            super(errorMessage);
        }
    }

    static constraints = {
        teamName blank: false
        rejectionDatetime nullable: true
        // timesRejected display: false
        enrolledTo validator: { league, enrollment -> league.hasRoomForEnrollments() }
    }
}
