package tp.avigliano

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class LeagueController {

    LeagueService leagueService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond leagueService.list(params), model:[leagueCount: leagueService.count()]
    }

    def show(Long id) {
        respond leagueService.get(id)
    }

    def create() {
        respond new League(params)
    }

    def save(League league) {
        if (league == null) {
            notFound()
            return
        }

        try {
            leagueService.save(league)
        } catch (ValidationException e) {
            respond league.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'league.label', default: 'League'), league.id])
                redirect league
            }
            '*' { respond league, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond leagueService.get(id)
    }

    def update(League league) {
        if (league == null) {
            notFound()
            return
        }

        try {
            leagueService.save(league)
        } catch (ValidationException e) {
            respond league.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'league.label', default: 'League'), league.id])
                redirect league
            }
            '*'{ respond league, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        leagueService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'league.label', default: 'League'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'league.label', default: 'League'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
