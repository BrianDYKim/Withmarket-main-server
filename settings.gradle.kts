rootProject.name = "yumarket"

include(
    ":application-query",
    ":domain-dynamo",
    ":domain-redis",
    ":domain-service",
    ":domain-shopcommand",
    ":common",
    ":client-query",
    ":client-command",
    ":client-kafka"
)