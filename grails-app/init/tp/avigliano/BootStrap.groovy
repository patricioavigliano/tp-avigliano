package tp.avigliano

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->
        loadData()
    }
    def destroy = {
    }

    @Transactional
    void loadData() {
        new League(name: 'FIUBA HOCKEY LEAGUE', correctionTimeWindow: 10, minTeams: 1, maxTeams: 2).save()
    }
}
