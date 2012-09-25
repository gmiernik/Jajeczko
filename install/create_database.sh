#!/bin/bash
export SQLTOOL=/home/test/hsqldb/lib/sqltool.jar
java -jar $SQLTOOL --rcfile=sqltool.rc jajeczko-prod create_database.sql
