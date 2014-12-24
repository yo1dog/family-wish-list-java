#!/bin/bash

# dumps the munch roles
pg_dumpall --roles-only | grep fwl > roles.psql