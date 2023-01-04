#!/bin/bash

RED=`tput setaf 1`
GREEN=`tput setaf 2`
BOLD=$(tput bold)
NC=`tput sgr0`

# In order to exit if any command fails
set -e

# keep track of the last executed command
trap 'last_command=$current_command; current_command=$BASH_COMMAND' DEBUG
# echo an error message before exiting
trap 'echo "\"${last_command}\" command filed with exit code $?."' EXIT

project_home="/home/dev/git/spring-media-organizer/spring-media-batch/src/test/resources/spring-media-batch/"

echo "##"
echo "## Test VideoClassifyByYearNominal1"
echo "##"

cd $project_home/VideoClassifyByYearNominal1

rm -Rf ./resources-result
rm -Rf ./result
mkdir -p ./resources-result
mkdir -p ./result

cd ./source
touch -m -a -t 201906180130.09 ./TearsOfSteel.mp4

echo "${GREEN}"
echo "OK"
echo "${NC}"



echo "##"
echo "## Test VideoClassifyByYearNominalVariousExtensions"
echo "##"

cd $project_home/VideoClassifyByYearNominalVariousExtensions

rm -Rf ./resources-result
rm -Rf ./result
mkdir -p ./resources-result
mkdir -p ./result

cd ./source
touch -m -a -t 201402180130.09 ./TearsOfSteel.mp4
touch -m -a -t 201402180130.09 ./SampleVideo_176x144_1mb.3gp
touch -m -a -t 201402180130.09 ./SampleVideo_1280x720_1mb.flv
touch -m -a -t 201402180130.09 ./SampleVideo_1280x720_1mb.mkv

touch -m -a -t 201906180130.09 ./drop.avi

touch -m -a -t 201512180130.09 ./grb_1.mpg

echo "${GREEN}"
echo "OK"
echo "${NC}"



echo "##"
echo "## Test PhotoClassifyByYearFileAlreadyExists"
echo "##"

cd $project_home/PhotoClassifyByYearFileAlreadyExists

rm -Rf ./resources-result
rm -Rf ./result
mkdir -p ./resources-result
mkdir -p ./result

cd ./source
touch -m -a -t 201904180130.09 ./Arlette4.jpg

echo "${GREEN}"
echo "OK"
echo "${NC}"



echo "##"
echo "## Test PhotoClassifyByYearNominal1"
echo "##"

cd $project_home/PhotoClassifyByYearNominal1

rm -Rf ./resources-result
rm -Rf ./result
mkdir -p ./resources-result
mkdir -p ./result

cd ./source
touch -m -a -t 201904180130.09 ./Arlette4.jpg

touch -m -a -t 201512180130.09 ./354PPP.jpg

echo "${GREEN}"
echo "OK"
echo "${NC}"



echo "##"
echo "## Test PhotoClassifyByYearNominalIgnoringNonImageFiles"
echo "##"

cd $project_home/PhotoClassifyByYearNominalIgnoringNonImageFiles

rm -Rf ./resources-result
rm -Rf ./result
mkdir -p ./resources-result
mkdir -p ./result

cd ./source
touch -m -a -t 201904180130.09 ./Arlette4.jpg

echo "${GREEN}"
echo "OK"
echo "${NC}"

