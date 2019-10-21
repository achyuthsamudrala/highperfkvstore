# kvstore
Key value store for high write throughput and read after write latency

This project aims at building a specific purpose key value store and achieve the following: </br>
1.) Isolated read snapshots </br>
2.) Atomic batch updates of the key value store </br>
3.) Range iteration: Iterate over ranges of key value pairs </br>
4.) Persistence </br>
5.) Get/Set/Delete values by key </br>

Some of the requirements and design of this are inspired from https://github.com/couchbase/moss
