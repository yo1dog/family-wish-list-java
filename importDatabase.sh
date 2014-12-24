#!/bin/bash

# imports the database dump and hides the schema error
cat database.psql | psql postgres 2>&1 | grep -v '^ERROR:  schema "public" already exists'