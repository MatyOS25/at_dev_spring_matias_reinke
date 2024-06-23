#!/bin/bash

set -e

host="$1"
port="$2"
shift 2
cmd="$@"

until nc -z -v -w30 "$host" "$port"
do
  echo "Waiting for $host:$port..."
  sleep 5
done

echo "$host:$port is up and running, executing command: $cmd"
exec $cmd
