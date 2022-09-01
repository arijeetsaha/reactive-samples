Functional Programming powered by-
1. Lambdas
2. Method References
3. Functional Interfaces

Promotes -
1. Behavioral parameterization
2. Immutability
3. concise code

Imperitive style vs functional style


### Performance Comparison:

| Operation                                    | Bulk Insert           | Insert each entity individually using flatMap                                         |
|----------------------------------------------|-----------------------|---------------------------------------------------------------------------------------|
| Code                                         | saveAll(merchantList) | Flux.fromIterable(merchantList).flatMap(merchant -> merchantRepository.save(merchant) |
| Performance <br/> (Insert 1000 new records ) | ~35.5s                | ~5.5s                                                                                 |



#### Performance screenshots-

1. [Bulk API SLA](Bulk.png) <br/>
2. [FlatMap API SLA](flatMap.png)


