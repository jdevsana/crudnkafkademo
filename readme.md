
Constraint:
1. Must have the Kafka running in the local machine with default port. Otherwise, you have to change the configurations in the KafkaConfig java file accordingly.
2. The consumer may return in appropriate error message. Because, API error or exception not handled, only the default error handler will response. 
3. All Kafka configuration in java file. If need to change the server address, port or topic name then need to change the configuration source code.
4. No Test case implemented.
5. Used in memory database H2 to save deploy me time.
6. ISBN must be valid ISBN number
7. Please, check the jdocs for more information