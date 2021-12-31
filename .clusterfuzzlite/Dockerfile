FROM gcr.io/oss-fuzz-base/base-builder@sha256:c0156890ec200317f5dba875137fe77835ee188d49feb7eb35c566d79e79c20e
RUN apt-get update && apt-get install -y ...    # Install required packages to build your project.
COPY . $SRC/<project_name>                      # Copy your project's source code.
WORKDIR $SRC/<project_name>                     # Working directory for build.sh.
COPY ./.clusterfuzzlite/build.sh $SRC/           # Copy build.sh into $SRC dir.