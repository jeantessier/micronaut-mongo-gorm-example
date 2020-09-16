# GORM Test

A trivial Micronaut app that uses GORM to talk to a Mongo database.

Either uncomment the port setting in `application.yml`, or replace `8080` in the
examples with the actual port the application is running under.

## Entity IDs

If I don't specify the `id` property of the entity class `User`, GORM will use
an integer sequence, just like any other entity class.

If I want to use UUIDs, I must specify the `id` property explicitly.

If I use `String` as the type of the `id` property, GORM will assign the string
version of a UUID to it.  It will be able to read and write entities with these
string values.  Looking inside MongoDB, the `_id` attribute will be a string
value.  But if I insert a new document in the collection using MongoDB, it will
receive an `ObjectId` value for its `_id` attribute.  GORM will be able to
print it correctly, but it will not be able to find the object by the string
value of that `ObjectId`.

If I use `org.bson.types.ObjectId` as the type of the `id` property, either GORM
or MongoDB will assign an `ObjectId` value to the `_id` attribute.  GORM will be
able to find documents by the string value of that `ObjectId`, but it will not
be able to print these values correctly.

## To Run

    $ ./gradlew run

> The examples below all use [Httpie](https://httpie.org/) to call the server
> from the command-line.  Feel free to use your favorite tool, such as
> [cURL](https://en.wikipedia.org/wiki/CURL) or
> [Postman](https://www.getpostman.com/) instead.  Httpie is a tool written in
> Python that installs itself in your local Python environment.  You can install
> it with:
>
> ```bash
> pip install httpie
> ```
>
> It assumes HTTP and `localhost` by default, so a call can be as simple as:
>
> ```bash
> http :3000
> ```

## Create a User

```bash
http :8080/users emailAddress=jean@jeantessier.com password=abcd1234 fullname="Jean Tessier"
```

or

```bash
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

```bash
http :8080/users
```
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

```bash
http :8080/users/5b835bd422351af19958ac55
```
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

```bash
http PATCH :8080/users/5b835bd422351af19958ac55 password=abc123
```

or

```bash
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

```bash
http DELETE :8080/users/5b835bd422351af19958ac55
```
```
OK
```

## Delete All Users

```bash
http DELETE :8080/users
```
```
Deleted 1 record.
```
