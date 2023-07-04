#!/bin/sh
mongosh notifications-db --eval "db.createUser({ user: 'user1', pwd: 'user1', roles: [{ role: 'dbOwner', db: 'notifications-db' }] })"