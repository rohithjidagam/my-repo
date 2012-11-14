package com.prokarma.pelops;

import java.util.List;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.scale7.cassandra.pelops.Bytes;
import org.scale7.cassandra.pelops.Cluster;
import org.scale7.cassandra.pelops.Mutator;
import org.scale7.cassandra.pelops.Pelops;
import org.scale7.cassandra.pelops.Selector;

public class MyPelops {

	public static void main(String[] args) throws Exception {

		String pool = "pool";
		String keyspace = "demo";
		String colFamily = "users";

		// init the connection pool
		Cluster cluster = new Cluster("localhost", 9160);
		Pelops.addPool(pool, cluster, keyspace);

		String rowKey = "bobbyjo";

		// write out some data
		/*Mutator mutator = Pelops.createMutator(pool);
		mutator.writeColumns(
				colFamily,
				rowKey,
				mutator.newColumnList(mutator.newColumn("name", "Dan"),
						mutator.newColumn("age", Bytes.fromInt(33))));
		mutator.execute(ConsistencyLevel.ONE);
*/
		// read back the data we just wrote
		Selector selector = Pelops.createSelector(pool);
		List<Column> columns = selector.getColumnsFromRow(colFamily, rowKey,
				false, ConsistencyLevel.ONE);

		System.out.println("Name: "
				+ Selector.getColumnStringValue(columns, "name"));
		System.out.println("Age: "
				+ Selector.getColumnValue(columns, "age").toInt());
		System.out.println(" Full Name: "
				+ Selector.getColumnStringValue(columns, "full_name"));
		System.out.println("Gender: "
				+ Selector.getColumnValue(columns, "gender").toUTF8());
		System.out.println("State: "
				+ Selector.getColumnStringValue(columns, "state"));
		System.out.println("Date Of Birth: "
				+ Selector.getColumnValue(columns, "birth_year"));
		System.out.println("Email Id: "
				+ Selector.getColumnValue(columns, "email").toUTF8());

		// shut down the pool
		Pelops.shutdown();

	}

}
