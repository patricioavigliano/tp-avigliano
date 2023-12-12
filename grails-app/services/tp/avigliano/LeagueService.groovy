package tp.avigliano

import grails.gorm.services.Service

@Service(Enrollment)
abstract class LeagueService implements ILeagueService {
    @Override
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

interface ILeagueService {
    League get(Serializable id)

    List<League> list(Map args)

    Long count()

    void delete(Serializable id)

    League save(League league)

    boolean hasRoomToEnrollNewTeam(Serializable id) 
}