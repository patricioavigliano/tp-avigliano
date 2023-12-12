package tp.avigliano

import grails.gorm.transactions.Transactional

@Transactional
class MyLeagueService {
    boolean hasRoomToEnrollNewTeam(Serializable leagueId){
        def cantidad = Enrollment.withCriteria {
            projections {
                countDistinct("id")
            }
            eq("enrolledTo", leagueId)
            ne("status", EnrollmentStatus.REJECTED)
        }
        League league = League.get(leagueId)
        // List<Enrollment> activeEnrollments = enrollments.find{ it.status == EnrollmentStatus.REQUESTED }
        printf "En la liga $league.name hay $cantidad equipos en proceso de inscripcion"
        return cantidad < league.maxTeams
    }
}