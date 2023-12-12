package tp.avigliano

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class LeagueServiceSpec extends Specification {

    LeagueService leagueService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new League(...).save(flush: true, failOnError: true)
        //new League(...).save(flush: true, failOnError: true)
        //League league = new League(...).save(flush: true, failOnError: true)
        //new League(...).save(flush: true, failOnError: true)
        //new League(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //league.id
    }

    void "test get"() {
        setupData()

        expect:
        leagueService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<League> leagueList = leagueService.list(max: 2, offset: 2)

        then:
        leagueList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        leagueService.count() == 5
    }

    void "test delete"() {
        Long leagueId = setupData()

        expect:
        leagueService.count() == 5

        when:
        leagueService.delete(leagueId)
        sessionFactory.currentSession.flush()

        then:
        leagueService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        League league = new League()
        leagueService.save(league)

        then:
        league.id != null
    }
}
