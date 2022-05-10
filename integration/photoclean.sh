#!/bin/sh
SCRIPT=$(readlink -f $0)
SCRIPTPATH=`dirname $SCRIPT`
java -jar $SCRIPTPATH/PhotoCleaner-shaded.jar "$@"
