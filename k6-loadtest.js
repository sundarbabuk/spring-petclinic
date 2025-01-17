import http from "k6/http";
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: "30s", target: 10 },
    { duration: "1m", target: 50 },
    { duration: "20s", target: 0 },
  ],
  thresholds: {
    http_req_duration: ["p(90)<200", "p(95)<300"],
    "http_req_duration{what:home}": [{
      thresholds: "p(95)<100",
      abortOnFail: true,
      delayAbortEval: "10s",
    }],
  }
};

export default function () {
  const pages = [
    "/posts/youtube/",
    "/posts/catalog/",
    "/this-does-not-exist/",
  ]
  for (const page of pages) {
    const resHome = http.get(
      "https://https://www.yahoo.com",
      {tags: {what: "home"}
    });
    check(resHome, {
      "status was 200": (r) => r.status == 200,
    });
    const resPage = http.get("https://www.yahoo.com" + page);
    check(resPage, {
      "status was 200": (r) => r.status == 200,
    });
    sleep(1);
  }
}