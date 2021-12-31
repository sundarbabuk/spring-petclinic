FROM gcr.io/oss-fuzz-base/base-builder

COPY . $SRC/curl
RUN git clone --depth 1 https://github.com/curl/curl-fuzzer.git /src/curl_fuzzer

# Use curl-fuzzer's scripts to get latest dependencies.
RUN $SRC/curl_fuzzer/scripts/ossfuzzdeps.sh

WORKDIR $SRC/curl_fuzzer
COPY .clusterfuzzlite/build.sh $SRC/