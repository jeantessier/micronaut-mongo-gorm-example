package mongo.test

import org.bson.types.ObjectId
import grails.gorm.annotation.*

@Entity
class User {

    ObjectId id

    String emailAddress
    String password
    String fullname

    static constraints = {
        emailAddress email: true
        password nullable: true
        fullname blank: false
    }

}
