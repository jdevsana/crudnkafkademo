Constraint:
1. Must have the Kafka running in the local machine with default port. Otherwise, need to change the configurations in the KafkaConfig java file accordingly.
2. To run the test cases need disable line 34 and enable line 35 in "src\main\java\com\sana\crudnkafkademo\config\KafkaConfig.java" to activate the embedded kafka. Otherwise some test case will not pass.
3. Used in-memory database H2 to save deployment time.
4. ISBN must be valid ISBN number
5. Please, check the jdocs for more information

