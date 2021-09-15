[![Travis CI Build Status](https://api.travis-ci.com/AndriyKalashnykov/spring-microservices-k8s.svg?branch=master)](https://travis-ci.com/github/AndriyKalashnykov/spring-microservices-k8s)
[![GitHub CI Status](https://github.com/AndriyKalashnykov/spring-microservices-k8s/workflows/ci/badge.svg)](https://github.com/AndriyKalashnykov/spring-microservices-k8s/actions?query=workflow%3Aci)
### Java Microservices with Spring Boot and Spring Cloud Kubernetes

- 쿠버네티스 환경에서 돌아가는 스프링 마이크로서비스 입니다.
- [VMWARE 가이드](https://tanzu.vmware.com/developer/guides/kubernetes/app-enhancements-spring-k8s) 로 제공하는 kubernetes spring 환경의 구현물입니다.
- [원본 소스 코드 주소](https://github.com/AndriyKalashnykov/spring-microservices-k8s) 입니다.
- [GPL3.0 라이선스](https://www.gnu.org/licenses/gpl-3.0.html) 에 의거해, 저자의 코드를 수정하고 공개합니다. 


### Pre-requisites

- OS: Mac or Linux
- [Docker](https://docs.docker.com/install/)
- [Minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)
- [Virtualbox](https://www.virtualbox.org/manual/ch02.html)
- [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
- [sdkman](https://sdkman.io/install)

    JDK 11.x
    
    ```shell
    sdk use java 11.0.10.hs-adpt
    ```
- [Apache Maven](https://maven.apache.org/install.html)
- [Curl](https://help.ubidots.com/en/articles/2165289-learn-how-to-install-run-curl-on-windows-macosx-linux)
- [HTTPie](https://httpie.org/doc#installation)
- [tree](http://mama.indstate.edu/users/ice/tree/)

### Clone repository

```bash
git clone git@github.com:AndriyKalashnykov/spring-microservices-k8s.git
```

### Start Kubernetes cluster

```bash
cd ./spring-microservices-k8s/scripts/
./start-cluster.sh
```

### Configure Kubernetes cluster

```bash
cd ./spring-microservices-k8s/scripts/
./setup-cluster.sh
```

### Deploy application to Kubernetes cluster

```bash
cd ./spring-microservices-k8s/scripts/
./install-all.sh
```

### Polulate test date

```bash
cd ./spring-microservices-k8s/scripts/
./populate-data.sh
```

### Observe Employee service logs

```bash
cd ./spring-microservices-k8s/scripts/
./employee-log.sh
```

### Open Swagger UI web interface

```bash
cd ./spring-microservices-k8s/scripts/
./gateway-open.sh
```

### Undeploy application from Kubernetes cluster

```bash
cd ./spring-microservices-k8s/scripts/
./delete-all.sh
```

### Delete Application specific Kubernetes cluster configuration (namespaces, clusterRole, etc.)

```bash
cd ./spring-microservices-k8s/scripts/
./destroy-cluster.sh
```

### Stop Kubernetes cluster

```bash
cd ./spring-microservices-k8s/scripts/
./stop-cluster.sh
```