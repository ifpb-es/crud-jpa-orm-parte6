# Ordem de apresentação da demo 

1. MainDataGenerator|MainDeleteAll
   1. MainSimpleQueries|[MainSimpleQueriesCriteria]
   1. MainFunctions|[MainFunctionsCriteria]

1. MainDataGeneratorGetAll|MainDeleteAllGetAll
   1. MainRetrieveTeacherGetAll
   1. MainRetrieveTeacherGetAllFetchEverything
   1. [MainRetrieveTeacherGetAllFetchEverythingEntityGraph]

1. MainSelectNPlusOneQueryProblem

1. MainSelectCartesianProductQueryProblem

# Observações com relação ao "JPA 2.1 Entity Graph":
- Foram utilizados "Set" em vez de "List" nas associações para evitar o erro do Hibernate (importante destacar que isso não resolve o problema do produto cartesiano): "javax.persistence.PersistenceException: org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags"
