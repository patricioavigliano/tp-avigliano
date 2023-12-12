package tp.avigliano

class League {
    def leagueService
    
    static hasMany = [enrollments:Enrollment]
    // List<Enrollment> enrollments
    String name
    Integer correctionTimeWindow = 1
    Integer minTeams = 2
    Integer maxTeams = 4


    static constraints = {
        name blank: false
        minTeams min: 1
        maxTeams min: 2
        correctionTimeWindow min: 1
        // enrollments validator: { enrollment, league -> league.hasRoomForEnrollments() }
    }

    boolean hasRoomForEnrollments() {
        return true
        return leagueService.hasRoomToEnrollNewTeam(id)
    }

    String toString() {
        return "$name"
    }
}
