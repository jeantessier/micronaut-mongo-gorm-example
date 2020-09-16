# GORM Test

A trivial Micronaut app that uses GORM to talk to a Mongo database.

Either uncomment the port setting in `application.yml`, or replace `8080` in the
examples with the actual port the application is running under.

## To Run

    $ ./gradlew run

> The examples below all use [Httpie](https://httpie.org/) to call the server
> from the command-line.  Feel free to use your favorite tool, such as
> [cURL](https://en.wikipedia.org/wiki/CURL) or
> [Postman](https://www.getpostman.com/) instead.  Httpie is a tool written in
> Python that installs itself in your local Python environment.  You can install
> it with:
>
> ```shell script
> pip install httpie
> ```
>
> It assumes HTTP and `localhost` by default, so a call can be as simple as:
>
> ```shell script
> http :3000
> ```

## Create a User

```shell script
http :8080/users emailAddress=jean@jeantessier.com password=abcd1234 fullname="Jean Tessier"
```

or

```shell script
curl \
    http://localhost:8080/users \
    -X POST \
    --header 'Content-type: application/json' \
    --data '{"emailAddress": "jean@jeantessier.com", "password": "abcd1234", "fullname": "Jean Tessier"}'
```

will outputs:

```json
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
```

## List All Users

```shell script
http :8080/users
```

will output:

```json
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
```

## Show One User

I had to cheat and use the `mongo` shell to pull the `id` value straight from
the database.  For some reason, Micronaut is showing an exploded view of the
`id` instead of the `ObjectId`.

```shell script
http :8080/users/5b835bd422351af19958ac55
```

will output:

```json
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
```

## Update a User

In this example, we're changing the password.

```shell script
http PATCH :8080/users/5b835bd422351af19958ac55 password=abc123
```

or

```shell script
curl \
    http://localhost:8080/users/5b835bd422351af19958ac55 \
    -X PATCH \
    --header 'Content-type: application/json' \
    --data '{"password": "abc123"}'
```

will output:

```json
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
```

## Delete a User

```shell script
http DELETE :8080/users/5b835bd422351af19958ac55
```

outputs:

```
OK
```

## Delete All Users

```shell script
http DELETE :8080/users
```

outputs:

```
Deleted 1 record.
```
