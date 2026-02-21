```
- Compilar para verificar
```
cd user-service
mvn clean compile
```

### 1.- Iniciar PostgreSQL para userdb
```
# Desde el directorio raíz del proyecto
docker-compose -f ../docker-compose.yml up -d

docker-compose down -v

# Ver logs
docker logs postgres-user
docker logs postgres-product
docker logs postgres-order

```

### 4.- Dockerizar

#### Compilar con perfil Kubernetes

```
mvn clean package -DskipTests
```
```
# Construir imagen
docker build -t user-service:1.0 .
docker build -t product-service:1.0 .
docker build -t order-service:1.0 .

# Este proceso toma 2-3 minutos la primera vez
# Ver progreso: [1/2] STEP X/Y...

# Verificar imagen creada
Linux/Mac: docker images | grep user-service
Window: docker images user-service
docker images product-service
docker images order-service

# Deberías ver:
# user-service   1.0   abc123def456   1 minute ago   230MB

```
#### Probar la imagen Docker

```
# Ejecutar contenedor de la app
docker run -p 8081:8081 \
-e SPRING_PROFILES_ACTIVE=kubernetes \
-e DB_URL=jdbc:postgresql://host.docker.internal:5434/userdb \
-e DB_USERNAME=postgres \
-e DB_PASSWORD=postgres \
user-service:1.0

docker run -p 8082:8082 \
-e SPRING_PROFILES_ACTIVE=kubernetes \
-e DB_URL=jdbc:postgresql://host.docker.internal:5433/userdb \
-e DB_USERNAME=postgres \
-e DB_PASSWORD=postgres \
product-service:1.0

docker run -p 8083:8083 \
-e SPRING_PROFILES_ACTIVE=kubernetes \
-e DB_URL=jdbc:postgresql://host.docker.internal:5435/userdb \
-e DB_USERNAME=postgres \
-e DB_PASSWORD=postgres \
order-service:1.0

```
### 3.- Desplegar en Kubernetes

- Configurar Contexto para Docker Desktop
```
# Ver los contextos
kubectl config get-contexts

# Cambiar al contexto de Docker Desktop
kubectl config use-context docker-desktop

```
- Borrar el despliegue de user-service 
```
# Borrar todo el namespace (incluye deployments, services, configmaps, secrets)
kubectl delete -f k8s/00-namespace.yaml
kubectl delete -f k8s/01-configmap.yaml
kubectl delete -f k8s/02-secret.yaml
kubectl delete -f k8s/03-deployment.yaml
kubectl delete -f k8s/04-service.yaml
```

- Volver a desplegar (dentro de cada microservicio)
```
kubectl apply -f k8s/00-namespace.yaml
kubectl apply -f k8s/01-configmap.yaml
kubectl apply -f k8s/02-secret.yaml
kubectl apply -f k8s/03-deployment.yaml
kubectl apply -f k8s/04-service.yaml
```

- Verificar el despliegue
```
# Verificar el deployment  
kubectl get deployments -n user-service

# Verificar Service
kubectl get service -n user-service
 
# Verificar pods
kubectl get pods -n user-service  
```

- En caso necesites redesplegar (por ejemplo, después de corregir un error en el Deployment):
```
 kubectl rollout restart deployment user-service -n user-service
```

- Ver logs
```
# Ver logs
kubectl logs -f <POD_NAME> -n user-service

# Ver descripción completa del pod
kubectl describe pod <POD_NAME> -n user-service

```
### 2.4.- Probar autenticación en Kubernetes

```
# Sin autenticación → 401
curl.exe http://localhost:30081/api/users

# Con ADMIN → 200
curl.exe -u juan.perez@example.com:admin123 http://localhost:30081/api/users

# Con USER intentando acceso ADMIN → 403
curl.exe -u maria.garcia@example.com:user123 http://localhost:30081/api/users
```
