package mongo.test

import io.micronaut.http.annotation.*
import io.micronaut.spring.tx.annotation.*
import java.text.*

@Controller("/users")
class UserController {

    @Get("/")
    @Transactional(readOnly = true)
    def index() {
        User.list()
    }

    @Get("/{id}")
    @Transactional(readOnly = true)
    def show(id) {
        User.get(id)
    }

    @Post("/")
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
