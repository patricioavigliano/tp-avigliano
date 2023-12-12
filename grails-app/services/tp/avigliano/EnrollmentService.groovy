package tp.avigliano

import grails.gorm.services.Service
import java.time.LocalDateTime

import Enrollment.*

@Service(Enrollment)
abstract class EnrollmentService implements IEnrollmentService {
    @Override
    Enrollment reject(Serializable id){ 
        Enrollment e = get(id)
        e.status = EnrollmentStatus.REJECTED
        e.rejectionDatetime = LocalDateTime.now()
        e.timesRejected += 1
        return save(e)
    }
    
    @Override
    Enrollment approve(Serializable id){ 
        Enrollment e = get(id)
        e.status = EnrollmentStatus.APPROVED
        return save(e)
    }

    @Override
    Enrollment create(Enrollment e){
        printf("enrollment %s ", e.enrolledTo)

        // if (e.teamName == "Hazard"){
        //     throw Exception("Hazard name invalid")
        // }
        return e
    }

    @Override
    Enrollment correct(Enrollment e){
        e.status = EnrollmentStatus.REQUESTED
        return save(e)
    }
}

interface IEnrollmentService {
    Enrollment get(Serializable id)

    List<Enrollment> list(Map args)

    Long count()

    void delete(Serializable id)

    Enrollment save(Enrollment enrollment)


    Enrollment create(Enrollment e)

    Enrollment correct(Enrollment enrollment)

    Enrollment reject(Serializable id)

    Enrollment approve(Serializable id)    
}