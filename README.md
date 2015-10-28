======================================================================
Publicador automatizado usando Docker para criação dos ambientes
======================================================================

- O projeto tem por objetivo automatizar a publicação de projetos no servidor de aplicações, criando ambientes virtualizados (containers) utilizando o docker para cada publicação solicitada.

- As configurações serão persistidas no banco de dados NoSql MongoDB.

- Tecnologias que serão utilizadas para realização deste projeto:
	<lu>
		<li>Java 7</li>
		<li>JSF 2.2.7</li>
		<li>PrimeFaces 5.2</li>
		<li>Bootstrap 3.3.1</li>
		<li>MongoDB</li>
		<li>Docker 1.8</li>
	</lu>

- Dockerizando o ambiente:

#iniciando o container do MongoDB, informe o ip do host ao realizar o binding das portas como no exemplo
docker run --name db -d -p 192.168.2.18:27017:27017 -v $HOME/docker/databases/mongodb/:/data/db mongo

#iniciando o container do glassfish
docker run --name glassfish -d -p 4848:4848 -p 8080:8080 --link db:mongo glassfish/mongo