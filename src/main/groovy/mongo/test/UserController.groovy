package mongo.test

import grails.gorm.transactions.Transactional
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn

@Controller("/users")
class UserController {

    @Get("/")
    @ExecuteOn(TaskExecutors.IO)
    @Transactional
    def index() {
        User.list()
    }

    @Get("/{id}")
    @ExecuteOn(TaskExecutors.IO)
    @Transactional
    def show(id) {
        User.get(id)
    }

    @Post("/")
    @ExecuteOn(TaskExecutors.IO)
    @Transactional
    def save(String emailAddress, String password, String fullname) {
        def user = new User(emailAddress: emailAddress, password: password, fullname: fullname)
        if (user.save()) {
            return user
        } else {
            return user.errors.allErrors.collect { MessageFormat.format(it.defaultMessage, it.arguments) }.join(", ")
        }
    }

    @Patch("/{id}")
    @ExecuteOn(TaskExecutors.IO)
    @Transactional
    def update(id, Optional<String> emailAddress, Optional<String> password, Optional<String> fullname) {
        def user = User.get(id)
        if (emailAddress.present) {
            user.emailAddress = emailAddress.get()
        }
        if (password.present) {
            user.password = password.get()
        }
        if (fullname.present) {
            user.fullname = fullname.get()
        }
        if (user.save()) {
            return user
        } else {
            return user.errors.allErrors.collect { MessageFormat.format(it.defaultMessage, it.arguments) }.join(", ")
        }
    }

    @Delete("/")
    @ExecuteOn(TaskExecutors.IO)
    @Transactional
    def delete() {
        def numberDeleted = 0
        User.list().each { user ->
            user.delete()
            numberDeleted++
        }

        return "Deleted ${numberDeleted} record${numberDeleted == 1 ? '' : 's'}."
    }

    @Delete("/{id}")
    @ExecuteOn(TaskExecutors.IO)
    @Transactional
    def delete(id) {
        def user = User.get(id)
        if (user) {
            user.delete()
            return "OK"
        } else {
            return "Could not delete user ${id}."
        }
    }

}
