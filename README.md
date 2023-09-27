# Nexus Sberhealth Security Plugin

mvn -PbuildKar clean install

docker exec -u root -t -i 4b9a062a6f04 /bin/bash

docker cp /home/jvvladimir/Документы/nexus-sberhealth-security-plugin-bundle.kar 4b9a062a6f04:/opt/sonatype/nexus/deploy/nexus-sberhealth-security-plugin-bundle.kar

docker logs -f --tail 100 4b9a062a6f04
