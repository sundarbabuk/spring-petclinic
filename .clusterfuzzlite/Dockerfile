FROM gcr.io/oss-fuzz-base/base-builder@sha256:c09c299d12a1aaac69adf601bcd3f1ad26eedd005a453a08a775a5d8641dcb9d       # Base image with clang toolchain
RUN apt-get update && apt-get install -y ...    # Install required packages to build your project.
COPY . $SRC/<project_name>                      # Copy your project's source code.
WORKDIR $SRC/<project_name>                     # Working directory for build.sh.
COPY ./.clusterfuzzlite/build.sh $SRC/           # Copy build.sh into $SRC dir.