# GORM Test

A trivial Micronaut app that uses GORM to talk to a Mongo database.

Either uncomment the port setting in `application.yml`, or replace `8080` in the
examples with the actual port the application is running under.

## To Run

    $ ./gradlew run

## Create a User

    $ curl \
        http://localhost:8080/users \
        -X POST \
        --header 'Content-type: application/json' \
        --data '{"emailAddress": "jean@jeantessier.com", "password": "abcd1234", "fullname": "Jean Tessier"}'
    {
        "emailAddress": "jean@jeantessier.com",
        "fullname": "Jean Tessier",
        "id": {
            "counter": 5811285,
            "date": 1535335380000,
            "machineIdentifier": 2241818,
            "processIdentifier": -3687,
            "time": 1535335380000,
            "timeSecond": 1535335380,
            "timestamp": 1535335380
        },
        "password": "abcd1234"
    }

## List All Users

    $ curl http://localhost:8080/users
    [
        {
            "emailAddress": "jean@jeantessier.com",
            "fullname": "Jean Tessier",
            "id": {
                "counter": 5811285,
                "date": 1535335380000,
                "machineIdentifier": 2241818,
                "processIdentifier": -3687,
                "time": 1535335380000,
                "timeSecond": 1535335380,
                "timestamp": 1535335380
            },
            "password": "abcd1234"
        }
    ]

## Show One User

I had to cheat and use the `mongo` shell to pull the `id` value straight from
the database.  For some reason, Micronaut is showing an exploded view of the
`id` instead of the `ObjectId`.

    $ curl http://localhost:8080/users/5b835bd422351af19958ac55
    {
        "emailAddress": "jean@jeantessier.com",
        "fullname": "Jean Tessier",
        "id": {
            "counter": 5811285,
            "date": 1535335380000,
            "machineIdentifier": 2241818,
            "processIdentifier": -3687,
            "time": 1535335380000,
            "timeSecond": 1535335380,
            "timestamp": 1535335380
        },
        "password": "abcd1234"
    }

## Update a User

In this example, we're changing the password.

    $ curl \
        http://localhost:8080/users/5b835bd422351af19958ac55 \
        -X PATCH \
        --header 'Content-type: application/json' \
        --data '{"password": "abc123"}'
    {
        "emailAddress": "jean@jeantessier.com",
        "fullname": "Jean Tessier",
        "id": {
            "counter": 5811285,
            "date": 1535335380000,
            "machineIdentifier": 2241818,
            "processIdentifier": -3687,
            "time": 1535335380000,
            "timeSecond": 1535335380,
            "timestamp": 1535335380
        },
        "password": "abc123"
    }

## Delete a User

    $ curl -X DELETE http://localhost:8080/users/5b835bd422351af19958ac55
    OK

## Delete All Users

    $ curl -X DELETE http://localhost:8080/users
    Deleted 1 record.
