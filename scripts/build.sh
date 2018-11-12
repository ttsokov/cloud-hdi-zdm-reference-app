#!/bin/bash

set -e

echo "Script phase ..."

ROOT_DIR=$(cd $(dirname $(dirname $0)) && pwd)
echo "ROOT_DIR=$ROOT_DIR"

MTAR_VERSION=0.0.1
BLUE_MTAR=cloud-hdi-zdm-ref-app-blue-$MTAR_VERSION.mtar
GREEN_MTAR=cloud-hdi-zdm-ref-app-green-$MTAR_VERSION.mtar
echo "BLUE_MTAR=$BLUE_MTAR"
echo "GREEN_MTAR=$GREEN_MTAR"

rm -rf $ROOT_DIR/out/

echo "Downloading MTA Archive Builder ..."

mkdir -p $ROOT_DIR/tmp/mta_archive_builder
wget -nv --output-document=$ROOT_DIR/tmp/mta_archive_builder/mta_archive_builder.jar --no-cookies --header "Cookie: eula_3_1_agreed=tools.hana.ondemand.com/developer-license-3_1.txt" https://tools.hana.ondemand.com/additional/mta_archive_builder-1.1.7.jar

echo "Building blue .mtar file ..."
cd $ROOT_DIR/cloud-hdi-zdm-ref-app.blue/mta-jee/
java -jar $ROOT_DIR/tmp/mta_archive_builder/mta_archive_builder.jar --build-target=CF --mtar=$BLUE_MTAR build
mkdir $ROOT_DIR/out/
cp $BLUE_MTAR $ROOT_DIR/out/
cd $ROOT_DIR

echo "Building green .mtar file ..."
cd $ROOT_DIR/cloud-hdi-zdm-ref-app.green/mta-jee/
java -jar $ROOT_DIR/tmp/mta_archive_builder/mta_archive_builder.jar --build-target=CF --mtar=$GREEN_MTAR build
cp $GREEN_MTAR $ROOT_DIR/out/
cd $ROOT_DIR

echo "ls -la $ROOT_DIR/out"
ls -la $ROOT_DIR/out
