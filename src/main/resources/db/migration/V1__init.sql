--
-- PostgreSQL database dump
--

-- Dumped from database version 11.3
-- Dumped by pg_dump version 11.3

-- Started on 2019-11-28 12:52:16

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 16401)
-- Name: pldbgapi; Type: EXTENSION; Schema: -; Owner: 
--

-- CREATE EXTENSION IF NOT EXISTS pldbgapi WITH SCHEMA public;


--
-- TOC entry 3016 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION pldbgapi; Type: COMMENT; Schema: -; Owner: 
--

-- COMMENT ON EXTENSION pldbgapi IS 'server-side support for debugging PL/pgSQL functions';


--
-- TOC entry 256 (class 1255 OID 16438)
-- Name: alquiladoEstadoPasaAlquillado(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."alquiladoEstadoPasaAlquillado"() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
	placa character varying;
	enc bool = true;
	myRecord RECORD;
BEGIN
	FOR myRecord IN SELECT * FROM "auto"
	JOIN "contrato" ON "auto"."placa" = "contrato"."placaAuto"
	WHERE myRecord."placa" = NEW.placaAuto AND enc = true
	LOOP
		myRecord."idEstado" = 2;		
		enc = false;
		RETURN NEW;
	END LOOP;
	
END;$$;


ALTER FUNCTION public."alquiladoEstadoPasaAlquillado"() OWNER TO postgres;

--
-- TOC entry 257 (class 1255 OID 16439)
-- Name: cantCarrosMarcaXModelo(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."cantCarrosMarcaXModelo"(marca integer, modelo integer) RETURNS integer
    LANGUAGE plpgsql
    AS $_$
DECLARE 
	cant integer;
BEGIN
	SELECT INTO cant COUNT(auto."placa")
	FROM public."auto" auto
	WHERE auto."idMarca" = $1 AND auto."idModelo" = $2;
	RETURN cant;
END;$_$;


ALTER FUNCTION public."cantCarrosMarcaXModelo"(marca integer, modelo integer) OWNER TO postgres;

--
-- TOC entry 258 (class 1255 OID 16440)
-- Name: cantDiasAlquilados(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."cantDiasAlquilados"("placaAuto" character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $_$DECLARE 
	cant integer = 0;
	fechaI date;
	fechaF date;
	diasP date;
	entro bool;
	myRecord RECORD;
BEGIN
	FOR myRecord IN SELECT * FROM "contrato"
	WHERE "contrato"."placaAuto" = $1
	LOOP
		--SELECT INTO placaAutoC "contrato"."placaAuto" 
		--FROM "contrato";
		IF myRecord."placaAuto" = $1 THEN --esto esta demas pero lo dejo por si acaso
			SELECT INTO fechaI myRecord."fechaInicio"; 

			SELECT INTO fechaF myRecord."fechaFin"; 

			SELECT INTO diasP myRecord."diasProrroga";
			entro = false;
			IF diasP > fechaF THEN
				cant = cant + (diasP-fechaI);
				entro = true;
			END IF;
			IF entro = false THEN
				cant = cant+(fechaF-fechaI);
			END IF;	
		END IF;		
	END LOOP;
	RETURN cant;
							 
END	$_$;


ALTER FUNCTION public."cantDiasAlquilados"("placaAuto" character varying) OWNER TO postgres;

--
-- TOC entry 239 (class 1255 OID 16441)
-- Name: eliminarAuto(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarAuto"("placaA" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	DELETE FROM "auto" WHERE "auto".placa = $1;
END;
$_$;


ALTER FUNCTION public."eliminarAuto"("placaA" character varying) OWNER TO postgres;

--
-- TOC entry 240 (class 1255 OID 16442)
-- Name: eliminarChofer(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarChofer"("idCh" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	DELETE FROM "chofer" WHERE "chofer".id = $1;
END;
$_$;


ALTER FUNCTION public."eliminarChofer"("idCh" character varying) OWNER TO postgres;

--
-- TOC entry 241 (class 1255 OID 16443)
-- Name: eliminarChoferConContrato_Trigger(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarChoferConContrato_Trigger"() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE 
	idChofer character varying;
BEGIN
	SELECT INTO idChofer "chofer".id
	FROM "chofer"
	JOIN "contrato" ON "chofer".id = "contrato".idChofer
	WHERE "contrato".idCh = OLD.id;

	IF(OLD.id = idChofer) THEN
		RAISE EXCEPTION 'No se puede eliminar un chofer con contrato(s)';
	END IF;
	
	RETURN OLD;
END;
$$;


ALTER FUNCTION public."eliminarChoferConContrato_Trigger"() OWNER TO postgres;

--
-- TOC entry 242 (class 1255 OID 16444)
-- Name: eliminarContrato(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarContrato"("idC" integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$
BEGIN
	DELETE FROM "contrato" WHERE "contrato"."id" = $1;
END;
$_$;


ALTER FUNCTION public."eliminarContrato"("idC" integer) OWNER TO postgres;

--
-- TOC entry 259 (class 1255 OID 16445)
-- Name: eliminarEstado(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarEstado"("idE" integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	DELETE FROM "estado" WHERE "estado".id = $1;
END;
$_$;


ALTER FUNCTION public."eliminarEstado"("idE" integer) OWNER TO postgres;

--
-- TOC entry 260 (class 1255 OID 16446)
-- Name: eliminarFormaPago(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarFormaPago"("idFP" integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	DELETE FROM "formaPago" WHERE "formaPago".id = $1;
END;
$_$;


ALTER FUNCTION public."eliminarFormaPago"("idFP" integer) OWNER TO postgres;

--
-- TOC entry 261 (class 1255 OID 16447)
-- Name: eliminarMarca(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarMarca"("idMa" integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	DELETE FROM "marca" WHERE "marca".id = $1;
END;
$_$;


ALTER FUNCTION public."eliminarMarca"("idMa" integer) OWNER TO postgres;

--
-- TOC entry 262 (class 1255 OID 16448)
-- Name: eliminarModelo(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarModelo"("idMo" integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	DELETE FROM "modelo" WHERE "modelo".id = $1;
END;
	$_$;


ALTER FUNCTION public."eliminarModelo"("idMo" integer) OWNER TO postgres;

--
-- TOC entry 263 (class 1255 OID 16449)
-- Name: eliminarPais(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarPais"("idP" integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	DELETE FROM "pais" WHERE "pais".id = $1;
END;
$_$;


ALTER FUNCTION public."eliminarPais"("idP" integer) OWNER TO postgres;

--
-- TOC entry 264 (class 1255 OID 16450)
-- Name: eliminarRol(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarRol"(cod_rol integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$
BEGIN
	DELETE FROM "rol" WHERE "rol"."cod_rol" = $1;
END;
$_$;


ALTER FUNCTION public."eliminarRol"(cod_rol integer) OWNER TO postgres;

--
-- TOC entry 265 (class 1255 OID 16451)
-- Name: eliminarTurista(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarTurista"("noPasT" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$
BEGIN
	DELETE FROM "turista"
	WHERE "turista"."noPasaporte" = $1;
END;
$_$;


ALTER FUNCTION public."eliminarTurista"("noPasT" character varying) OWNER TO postgres;

--
-- TOC entry 266 (class 1255 OID 16452)
-- Name: eliminarTuristaContrato(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."eliminarTuristaContrato"() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE 
	noPasT1 character varying;
	idCont integer;
BEGIN
	SELECT INTO idCont "contrato"."id"
	FROM "turista" turista, "contrato" contrato
	WHERE turista."nopasaporte" = contrato."nopast" 
	AND contrato."nopast" = OLD."nopasaporte"
	LIMIT 1;

		PERFORM "eliminarContrato"(idCont);
		--RAISE EXCEPTION 'Algunos contratos han sido eliminados';

	
	RETURN OLD;
END;
$$;


ALTER FUNCTION public."eliminarTuristaContrato"() OWNER TO postgres;

--
-- TOC entry 267 (class 1255 OID 16453)
-- Name: ingresoAnual(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."ingresoAnual"(anno integer) RETURNS double precision
    LANGUAGE plpgsql
    AS $_$DECLARE 
	total double precision = 0;
	fecha date;
	anno integer;
	myRecord RECORD;
BEGIN
	--SELECT INTO fecha 
	FOR myRecord IN SELECT * FROM "contrato"
	WHERE (SELECT EXTRACT(YEAR FROM "contrato"."fechaInicio")) = $1
	LOOP
		total = total + myRecord."tarifa";
	END LOOP;
	RETURN total;

END;
	$_$;


ALTER FUNCTION public."ingresoAnual"(anno integer) OWNER TO postgres;

--
-- TOC entry 268 (class 1255 OID 16454)
-- Name: ingresoMensual(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."ingresoMensual"(mes integer, anno integer) RETURNS double precision
    LANGUAGE plpgsql
    AS $_$DECLARE 
	total double precision = 0;
	fecha date;
	anno integer;
	myRecord RECORD;
BEGIN
	FOR myRecord IN SELECT * FROM "contrato"
	WHERE (SELECT EXTRACT(YEAR FROM "contrato"."fechaInicio")) = $2 AND
	(SELECT EXTRACT(MONTH FROM "contrato"."fechaInicio")) = $1 
	LOOP
		total = total + myRecord."tarifa";
	END LOOP;
	RETURN total;

END;
	$_$;


ALTER FUNCTION public."ingresoMensual"(mes integer, anno integer) OWNER TO postgres;

--
-- TOC entry 269 (class 1255 OID 16455)
-- Name: ingresosCheques(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."ingresosCheques"("placaAuto" character varying) RETURNS double precision
    LANGUAGE plpgsql
    AS $_$DECLARE 
	total double precision = 0.0;
	myRecord RECORD;
BEGIN
	FOR myRecord IN SELECT * FROM "contrato"
	WHERE "contrato"."placaAuto" = $1
	LOOP
		IF myRecord."idFP" = 9 THEN
		total = total + myRecord."tarifa";
		END IF;
	END LOOP;
	RETURN total;
END;$_$;


ALTER FUNCTION public."ingresosCheques"("placaAuto" character varying) OWNER TO postgres;

--
-- TOC entry 270 (class 1255 OID 16456)
-- Name: ingresosEfectivo(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."ingresosEfectivo"("placaAuto" character varying) RETURNS double precision
    LANGUAGE plpgsql
    AS $_$DECLARE 
	total double precision = 0.0;
	myRecord RECORD;
	idFP integer;
BEGIN
	FOR myRecord IN SELECT * FROM "contrato"
	WHERE "contrato"."placaAuto" = $1
	LOOP
		SELECT INTO idFP myRecord."idFP";
		IF idFP = 8 THEN
		total = total + myRecord."tarifa";
		END IF;
	END LOOP;
	RETURN total;
END;$_$;


ALTER FUNCTION public."ingresosEfectivo"("placaAuto" character varying) OWNER TO postgres;

--
-- TOC entry 271 (class 1255 OID 16457)
-- Name: ingresosTarjetaCred(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."ingresosTarjetaCred"("placaAuto" character varying) RETURNS double precision
    LANGUAGE plpgsql
    AS $_$DECLARE 
	total double precision = 0.0;
	myRecord RECORD;
BEGIN
	FOR myRecord IN SELECT * FROM "contrato"
	WHERE "contrato"."placaAuto" = $1
	LOOP
		IF myRecord."idFP" = 10 THEN
		total = total + myRecord."tarifa";
		END IF;
	END LOOP;
	RETURN total;
END;$_$;


ALTER FUNCTION public."ingresosTarjetaCred"("placaAuto" character varying) OWNER TO postgres;

--
-- TOC entry 272 (class 1255 OID 16458)
-- Name: ingresosTotalMarcaXModelo(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."ingresosTotalMarcaXModelo"(marca integer, modelo integer) RETURNS double precision
    LANGUAGE plpgsql
    AS $_$DECLARE 
	total double precision = 0.0;
	myRecord RECORD;
BEGIN
	FOR myRecord IN SELECT * FROM "contrato", "auto"
	WHERE "contrato"."placaAuto" = "auto"."placa" AND
	"auto"."idMarca" = $1 AND "auto"."idModelo" = $2
	
	LOOP
		IF myRecord."idFP" = 8 THEN
			total = total + myRecord."tarifa";
		END IF;
	END LOOP;
	RETURN total;
END;$_$;


ALTER FUNCTION public."ingresosTotalMarcaXModelo"(marca integer, modelo integer) OWNER TO postgres;

--
-- TOC entry 273 (class 1255 OID 16459)
-- Name: ingresosTotalXMarca(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."ingresosTotalXMarca"(marca integer) RETURNS double precision
    LANGUAGE plpgsql
    AS $_$DECLARE 
	total double precision = 0.0;
	myRecord RECORD;
BEGIN
	FOR myRecord IN SELECT * FROM "contrato", "auto"
	WHERE "auto"."placa" = "contrato"."placaAuto" 
	AND auto."idMarca" = $1
	LOOP
		total = total + myRecord."tarifa";
		
	END LOOP;
	RETURN total;
END;$_$;


ALTER FUNCTION public."ingresosTotalXMarca"(marca integer) OWNER TO postgres;

--
-- TOC entry 274 (class 1255 OID 16460)
-- Name: insertUser(character varying, character varying, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."insertUser"(username character varying, password character varying, enabled boolean) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
	INSERT INTO "users" VALUES ("username", "password", "enabled");
END
$$;


ALTER FUNCTION public."insertUser"(username character varying, password character varying, enabled boolean) OWNER TO postgres;

--
-- TOC entry 275 (class 1255 OID 16461)
-- Name: insertarAuto(character varying, integer, integer, integer, double precision, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."insertarAuto"("placaA" character varying, "idMo" integer, "idMa" integer, "idE" integer, "cantKmA" double precision, color character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN
	INSERT INTO "auto" VALUES ("placaA", "idMo", "idMa", "idE", "cantKmA", "color");
END;
$$;


ALTER FUNCTION public."insertarAuto"("placaA" character varying, "idMo" integer, "idMa" integer, "idE" integer, "cantKmA" double precision, color character varying) OWNER TO postgres;

--
-- TOC entry 276 (class 1255 OID 16462)
-- Name: insertarChofer(character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."insertarChofer"("idCh" character varying, "nombreCh" character varying, "direcCh" character varying, "categoriaCh" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
	INSERT INTO "chofer" VALUES ("idCh", "nombreCh", "direcCh", "categoriaCh");
END;
$$;


ALTER FUNCTION public."insertarChofer"("idCh" character varying, "nombreCh" character varying, "direcCh" character varying, "categoriaCh" character varying) OWNER TO postgres;

--
-- TOC entry 277 (class 1255 OID 16463)
-- Name: insertarContrato(date, date, integer, date, double precision, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."insertarContrato"("fechaI" date, "fechaF" date, "idFP" integer, "diasProrroga" date, tarifa double precision, "idCh" character varying, "placaAuto" character varying, "noPasT" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	INSERT INTO "contrato" ("fechaInicio", "fechaFin",
	"idFP", "diasProrroga", "tarifa", "idCh", "placaAuto",
	"noPasT") VALUES ($1, $2, $3, $4, $5, $6, $7, $8);
END;
$_$;


ALTER FUNCTION public."insertarContrato"("fechaI" date, "fechaF" date, "idFP" integer, "diasProrroga" date, tarifa double precision, "idCh" character varying, "placaAuto" character varying, "noPasT" character varying) OWNER TO postgres;

--
-- TOC entry 278 (class 1255 OID 16464)
-- Name: insertarEstado(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."insertarEstado"("nombreE" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	INSERT INTO "estado" ("nombre") VALUES ($1);
END;$_$;


ALTER FUNCTION public."insertarEstado"("nombreE" character varying) OWNER TO postgres;

--
-- TOC entry 279 (class 1255 OID 16465)
-- Name: insertarFormaPago(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."insertarFormaPago"("nombreFP" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	INSERT INTO "formaPago" ("nombre") VALUES ($1);
END;
	$_$;


ALTER FUNCTION public."insertarFormaPago"("nombreFP" character varying) OWNER TO postgres;

--
-- TOC entry 280 (class 1255 OID 16466)
-- Name: insertarMarca(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."insertarMarca"("nombreMa" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	INSERT INTO "marca" ("nombre") VALUES ($1);
END;
$_$;


ALTER FUNCTION public."insertarMarca"("nombreMa" character varying) OWNER TO postgres;

--
-- TOC entry 281 (class 1255 OID 16467)
-- Name: insertarModelo(integer, character varying, double precision, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."insertarModelo"("idMa" integer, "nombreMo" character varying, "tarifaMo" double precision, cantidad integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
	INSERT INTO "modelo" ("idMarca", "nombre",
	"tarifa") VALUES ($1, $2, $3);
END;
$_$;


ALTER FUNCTION public."insertarModelo"("idMa" integer, "nombreMo" character varying, "tarifaMo" double precision, cantidad integer) OWNER TO postgres;

--
-- TOC entry 282 (class 1255 OID 16468)
-- Name: insertarPais(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."insertarPais"("nombreP" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
	INSERT INTO "pais" ("nombre") VALUES ($1);
END;
$_$;


ALTER FUNCTION public."insertarPais"("nombreP" character varying) OWNER TO postgres;

--
-- TOC entry 283 (class 1255 OID 16469)
-- Name: insertarRol(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."insertarRol"(nombre character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
	INSERT INTO "rol" ("desc_rol") VALUES ($1);
END;$_$;


ALTER FUNCTION public."insertarRol"(nombre character varying) OWNER TO postgres;

--
-- TOC entry 284 (class 1255 OID 16470)
-- Name: insertarTurista(character varying, character varying, date, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."insertarTurista"("noPasT" character varying, "nombreT" character varying, "fechaNacT" date, "sexoT" character varying, "idP" integer) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN
	INSERT INTO "turista" VALUES ("noPasT", "nombreT", "fechaNacT", "sexoT", "idP");
END;
$$;


ALTER FUNCTION public."insertarTurista"("noPasT" character varying, "nombreT" character varying, "fechaNacT" date, "sexoT" character varying, "idP" integer) OWNER TO postgres;

--
-- TOC entry 285 (class 1255 OID 16471)
-- Name: modificarAuto(character varying, integer, integer, integer, double precision, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."modificarAuto"("placaA" character varying, "idMo" integer, "idMa" integer, "idE" integer, "cantKmA" double precision, color character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	UPDATE "auto" SET "placa" = $1, "idModelo" = $2, "idMarca" = $3, "idEstado" = $4, "cantKm" = $5, "color" = $6 
	WHERE "auto".placa = $1;
END;
$_$;


ALTER FUNCTION public."modificarAuto"("placaA" character varying, "idMo" integer, "idMa" integer, "idE" integer, "cantKmA" double precision, color character varying) OWNER TO postgres;

--
-- TOC entry 286 (class 1255 OID 16472)
-- Name: modificarChofer(character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."modificarChofer"("idCh" character varying, "nombreCh" character varying, "direcCh" character varying, "categoriaCh" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	UPDATE "chofer" SET "id" = $1, "nombre" = $2, "direccion" = $3, "categoria" = $4
	WHERE "chofer".id = $1;
END;
$_$;


ALTER FUNCTION public."modificarChofer"("idCh" character varying, "nombreCh" character varying, "direcCh" character varying, "categoriaCh" character varying) OWNER TO postgres;

--
-- TOC entry 287 (class 1255 OID 16473)
-- Name: modificarContrato(integer, date, date, integer, date, double precision, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."modificarContrato"("idC" integer, "fechaI" date, "fechaF" date, "idFP" integer, "diasProrroga" date, tarifa double precision, "idCh" character varying, "placaA" character varying, "noPasT" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	UPDATE "contrato" SET "fechaInicio" = $2, "fechaFin" = $3,
	"idFP" = $4, "diasProrroga" = $5, "tarifa" = $6,
	"idCh" = $7, "placaAuto" = $8, "noPasT" = $9
	WHERE "contrato"."id" = $1;
END;$_$;


ALTER FUNCTION public."modificarContrato"("idC" integer, "fechaI" date, "fechaF" date, "idFP" integer, "diasProrroga" date, tarifa double precision, "idCh" character varying, "placaA" character varying, "noPasT" character varying) OWNER TO postgres;

--
-- TOC entry 288 (class 1255 OID 16474)
-- Name: modificarEstado(integer, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."modificarEstado"("idE" integer, "nombreE" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 	
	UPDATE "estado" SET "nombre" = $2 WHERE "estado".id = $1;
END;
$_$;


ALTER FUNCTION public."modificarEstado"("idE" integer, "nombreE" character varying) OWNER TO postgres;

--
-- TOC entry 289 (class 1255 OID 16475)
-- Name: modificarFormaPago(integer, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."modificarFormaPago"("idFP" integer, "nombreFP" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	UPDATE "formaPago" SET "nombre" = $2 
	WHERE "formaPago".id = $1;
END;$_$;


ALTER FUNCTION public."modificarFormaPago"("idFP" integer, "nombreFP" character varying) OWNER TO postgres;

--
-- TOC entry 290 (class 1255 OID 16476)
-- Name: modificarMarca(integer, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."modificarMarca"("idMa" integer, "nombreMa" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	UPDATE "marca" SET "nombre" = $2 WHERE "marca".id = $1;
END;
$_$;


ALTER FUNCTION public."modificarMarca"("idMa" integer, "nombreMa" character varying) OWNER TO postgres;

--
-- TOC entry 291 (class 1255 OID 16477)
-- Name: modificarModelo(integer, integer, character varying, double precision, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."modificarModelo"("idMo" integer, "idMa" integer, "nombreMo" character varying, "tarifaMo" double precision, cantidad integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
	UPDATE "modelo" SET "idMarca" = $2, "nombre" = $3,
	"tarifa" = $4, "cantidad" = $5 WHERE "modelo".id = $1;
END;
$_$;


ALTER FUNCTION public."modificarModelo"("idMo" integer, "idMa" integer, "nombreMo" character varying, "tarifaMo" double precision, cantidad integer) OWNER TO postgres;

--
-- TOC entry 292 (class 1255 OID 16478)
-- Name: modificarPais(integer, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."modificarPais"("idP" integer, "nombreP" character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	UPDATE "pais" SET "nombre" = $2 
	WHERE "pais".id = $1;
END;
$_$;


ALTER FUNCTION public."modificarPais"("idP" integer, "nombreP" character varying) OWNER TO postgres;

--
-- TOC entry 293 (class 1255 OID 16479)
-- Name: modificarRol(integer, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."modificarRol"(cod_rol integer, desc_rol character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$
BEGIN
	UPDATE "rol" SET "desc_rol" = $2 WHERE cod_rol = $1;
END;
$_$;


ALTER FUNCTION public."modificarRol"(cod_rol integer, desc_rol character varying) OWNER TO postgres;

--
-- TOC entry 294 (class 1255 OID 16480)
-- Name: modificarTurista(character varying, character varying, date, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."modificarTurista"("noPasT" character varying, "nombreT" character varying, "fechaNacT" date, "sexoT" character varying, "idP" integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	UPDATE "turista" SET "nombre" = $2, "fechaNacimiento" = $3,
	"sexo" = $4, "idPais" = $5 WHERE "turista"."noPasaporte" = $1;
END;
$_$;


ALTER FUNCTION public."modificarTurista"("noPasT" character varying, "nombreT" character varying, "fechaNacT" date, "sexoT" character varying, "idP" integer) OWNER TO postgres;

--
-- TOC entry 295 (class 1255 OID 16481)
-- Name: obtenerIdModelo(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."obtenerIdModelo"("placaA" character varying, OUT "idMo" integer) RETURNS integer
    LANGUAGE plpgsql
    AS $_$
BEGIN
	SELECT INTO "idMo" "Modelo"."id"
	FROM "Modelo"
	JOIN "Auto" ON "Modelo"."id" = "Auto"."idModelo"
	WHERE "Auto"."placa" = $1;
END;
$_$;


ALTER FUNCTION public."obtenerIdModelo"("placaA" character varying, OUT "idMo" integer) OWNER TO postgres;

--
-- TOC entry 296 (class 1255 OID 16482)
-- Name: obtenerNombresTuristas(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."obtenerNombresTuristas"(OUT nombres refcursor) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$BEGIN
	OPEN nombres FOR
	SELECT "Turista".nombre
	FROM "Turista";
END;
$$;


ALTER FUNCTION public."obtenerNombresTuristas"(OUT nombres refcursor) OWNER TO postgres;

--
-- TOC entry 297 (class 1255 OID 16483)
-- Name: obtenerTarifaModelo(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."obtenerTarifaModelo"("idMo" integer, OUT tarifa double precision) RETURNS double precision
    LANGUAGE plpgsql
    AS $_$BEGIN 
	SELECT INTO tarifa "modelo".tarifa
	FROM "modelo"
	JOIN "marca" ON "modelo"."idMarca" = "marca"."id"
	WHERE "modelo"."id" = $1;
END;
$_$;


ALTER FUNCTION public."obtenerTarifaModelo"("idMo" integer, OUT tarifa double precision) OWNER TO postgres;

--
-- TOC entry 298 (class 1255 OID 16484)
-- Name: usuarioInsert(character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."usuarioInsert"(nom character varying, nom_usuario character varying, password character varying, cod_rol integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN
	INSERT INTO "usuario" ("nom","nom_usuario", "password", "cod_rol")
	VALUES ($1, $2, $3, $4);
	--WHERE "usuario"."cod_rol" = $4;
END;
$_$;


ALTER FUNCTION public."usuarioInsert"(nom character varying, nom_usuario character varying, password character varying, cod_rol integer) OWNER TO postgres;

--
-- TOC entry 299 (class 1255 OID 16485)
-- Name: usuario_delete(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.usuario_delete(cod_usuario integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$
BEGIN
	DELETE FROM "usuario" WHERE "usuario"."cod_usuario" = $1;
END;
$_$;


ALTER FUNCTION public.usuario_delete(cod_usuario integer) OWNER TO postgres;

--
-- TOC entry 300 (class 1255 OID 16486)
-- Name: usuario_update(integer, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.usuario_update(cod_usuario integer, nom character varying, nom_usuario character varying, password character varying, cod_rol integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$
BEGIN
	UPDATE "usuario" SET "nom" = $2,
	"nom_usuario" = $3, "password" = $4, "cod_rol" = $5
	WHERE "usuario"."cod_usuario" = $1;
END;
$_$;


ALTER FUNCTION public.usuario_update(cod_usuario integer, nom character varying, nom_usuario character varying, password character varying, cod_rol integer) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 202 (class 1259 OID 16487)
-- Name: contrato; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contrato (
    id integer NOT NULL,
    fechainicio date,
    fechafin date,
    idfp integer NOT NULL,
    tarifa double precision,
    idch character varying,
    placaauto character varying NOT NULL,
    nopast character varying NOT NULL,
    diasprorroga date
);


ALTER TABLE public.contrato OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16493)
-- Name: Contrato_idFP_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Contrato_idFP_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Contrato_idFP_seq" OWNER TO postgres;

--
-- TOC entry 3017 (class 0 OID 0)
-- Dependencies: 203
-- Name: Contrato_idFP_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Contrato_idFP_seq" OWNED BY public.contrato.idfp;


--
-- TOC entry 204 (class 1259 OID 16495)
-- Name: Contrato_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Contrato_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Contrato_id_seq" OWNER TO postgres;

--
-- TOC entry 3018 (class 0 OID 0)
-- Dependencies: 204
-- Name: Contrato_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Contrato_id_seq" OWNED BY public.contrato.id;


--
-- TOC entry 205 (class 1259 OID 16497)
-- Name: estado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estado (
    id integer NOT NULL,
    nombre character varying
);


ALTER TABLE public.estado OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16503)
-- Name: Estado_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Estado_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Estado_id_seq" OWNER TO postgres;

--
-- TOC entry 3019 (class 0 OID 0)
-- Dependencies: 206
-- Name: Estado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Estado_id_seq" OWNED BY public.estado.id;


--
-- TOC entry 207 (class 1259 OID 16505)
-- Name: formapago; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.formapago (
    id integer NOT NULL,
    nombre character varying
);


ALTER TABLE public.formapago OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16511)
-- Name: FormaPago_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."FormaPago_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."FormaPago_id_seq" OWNER TO postgres;

--
-- TOC entry 3020 (class 0 OID 0)
-- Dependencies: 208
-- Name: FormaPago_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."FormaPago_id_seq" OWNED BY public.formapago.id;


--
-- TOC entry 209 (class 1259 OID 16513)
-- Name: marca; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.marca (
    id integer NOT NULL,
    nombre character varying
);


ALTER TABLE public.marca OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16519)
-- Name: Marca_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Marca_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Marca_id_seq" OWNER TO postgres;

--
-- TOC entry 3021 (class 0 OID 0)
-- Dependencies: 210
-- Name: Marca_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Marca_id_seq" OWNED BY public.marca.id;


--
-- TOC entry 211 (class 1259 OID 16521)
-- Name: modelo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.modelo (
    id integer NOT NULL,
    idmarca integer NOT NULL,
    nombre character varying,
    tarifa double precision
);


ALTER TABLE public.modelo OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16527)
-- Name: Modelo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Modelo_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Modelo_id_seq" OWNER TO postgres;

--
-- TOC entry 3022 (class 0 OID 0)
-- Dependencies: 212
-- Name: Modelo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Modelo_id_seq" OWNED BY public.modelo.id;


--
-- TOC entry 213 (class 1259 OID 16529)
-- Name: pais; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pais (
    id integer NOT NULL,
    nombre character varying
);


ALTER TABLE public.pais OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16535)
-- Name: Pais_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Pais_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Pais_id_seq" OWNER TO postgres;

--
-- TOC entry 3023 (class 0 OID 0)
-- Dependencies: 214
-- Name: Pais_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Pais_id_seq" OWNED BY public.pais.id;


--
-- TOC entry 215 (class 1259 OID 16545)
-- Name: turista; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.turista (
    nopasaporte character varying NOT NULL,
    nombre character varying,
    fechanacimiento date,
    sexo character varying(1),
    idpais integer NOT NULL
);


ALTER TABLE public.turista OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16551)
-- Name: Turista_idPais_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Turista_idPais_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Turista_idPais_seq" OWNER TO postgres;

--
-- TOC entry 3024 (class 0 OID 0)
-- Dependencies: 216
-- Name: Turista_idPais_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Turista_idPais_seq" OWNED BY public.turista.idpais;


--
-- TOC entry 217 (class 1259 OID 16558)
-- Name: auto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auto (
    placa character varying(7) NOT NULL,
    idmodelo integer NOT NULL,
    idestado integer NOT NULL,
    cantkm double precision,
    color character varying
);


ALTER TABLE public.auto OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16564)
-- Name: chofer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chofer (
    id character varying NOT NULL,
    nombre character varying,
    direccion character varying,
    categoria character varying(1)
);


ALTER TABLE public.chofer OWNER TO postgres;

--
-- TOC entry 2831 (class 2604 OID 16582)
-- Name: contrato id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contrato ALTER COLUMN id SET DEFAULT nextval('public."Contrato_id_seq"'::regclass);


--
-- TOC entry 2832 (class 2604 OID 16583)
-- Name: contrato idfp; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contrato ALTER COLUMN idfp SET DEFAULT nextval('public."Contrato_idFP_seq"'::regclass);


--
-- TOC entry 2833 (class 2604 OID 16584)
-- Name: estado id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estado ALTER COLUMN id SET DEFAULT nextval('public."Estado_id_seq"'::regclass);


--
-- TOC entry 2834 (class 2604 OID 16585)
-- Name: formapago id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formapago ALTER COLUMN id SET DEFAULT nextval('public."FormaPago_id_seq"'::regclass);


--
-- TOC entry 2835 (class 2604 OID 16586)
-- Name: marca id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.marca ALTER COLUMN id SET DEFAULT nextval('public."Marca_id_seq"'::regclass);


--
-- TOC entry 2836 (class 2604 OID 16587)
-- Name: modelo id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modelo ALTER COLUMN id SET DEFAULT nextval('public."Modelo_id_seq"'::regclass);


--
-- TOC entry 2837 (class 2604 OID 16588)
-- Name: pais id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pais ALTER COLUMN id SET DEFAULT nextval('public."Pais_id_seq"'::regclass);


--
-- TOC entry 2838 (class 2604 OID 16590)
-- Name: turista idpais; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turista ALTER COLUMN idpais SET DEFAULT nextval('public."Turista_idPais_seq"'::regclass);


--
-- TOC entry 3009 (class 0 OID 16558)
-- Dependencies: 217
-- Data for Name: auto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.auto (placa, idmodelo, idestado, cantkm, color) VALUES ('T678965', 3, 6, 90, 'Negro');
INSERT INTO public.auto (placa, idmodelo, idestado, cantkm, color) VALUES ('T678905', 3, 2, 9, 'Blanco');
INSERT INTO public.auto (placa, idmodelo, idestado, cantkm, color) VALUES ('T678954', 5, 3, 90, 'Azul');
INSERT INTO public.auto (placa, idmodelo, idestado, cantkm, color) VALUES ('T678543', 6, 3, 0, 'Blanco');
INSERT INTO public.auto (placa, idmodelo, idestado, cantkm, color) VALUES ('T348906', 5, 3, 67, 'Blanco');
INSERT INTO public.auto (placa, idmodelo, idestado, cantkm, color) VALUES ('T128907', 5, 3, 34, 'Negro');
INSERT INTO public.auto (placa, idmodelo, idestado, cantkm, color) VALUES ('T678432', 3, 3, 7, 'Blanco');
INSERT INTO public.auto (placa, idmodelo, idestado, cantkm, color) VALUES ('T678952', 4, 2, 30, 'Azul');


--
-- TOC entry 3010 (class 0 OID 16564)
-- Dependencies: 218
-- Data for Name: chofer; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.chofer (id, nombre, direccion, categoria) VALUES ('98100407100', 'Abelardo Chaviano Fajardo', '68 E/ 11 y 13', 'B');
INSERT INTO public.chofer (id, nombre, direccion, categoria) VALUES ('99062708332', 'Veronica Labrada Peña', 'Tulipan y Loma', 'A');
INSERT INTO public.chofer (id, nombre, direccion, categoria) VALUES ('01111834567', 'Gabriela Chaviano Fajardo', '68 E/ 11 y 13', 'B');
INSERT INTO public.chofer (id, nombre, direccion, categoria) VALUES ('97061508332', 'Nemo Gonzalez Perez', '68 e/ 11 y 23', 'B');
INSERT INTO public.chofer (id, nombre, direccion, categoria) VALUES ('97051108332', 'Marcos Vilareño Pacheco', 'Roma e/ Calzada y Rosales', 'A');
INSERT INTO public.chofer (id, nombre, direccion, categoria) VALUES ('12345678907', 'Alina Perez del Rosio', 'Calle K e/ 11 y 13', 'C');
INSERT INTO public.chofer (id, nombre, direccion, categoria) VALUES ('82050108332', 'Yunia Peña Ruiz', 'Tulipan esq. Loma no. 1012', 'C');


--
-- TOC entry 2994 (class 0 OID 16487)
-- Dependencies: 202
-- Data for Name: contrato; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.contrato (id, fechainicio, fechafin, idfp, tarifa, idch, placaauto, nopast, diasprorroga) VALUES (30, '2019-04-01', '2019-04-03', 9, 1764, '01111834567', 'T678905', 'P87654', '2019-04-19');
INSERT INTO public.contrato (id, fechainicio, fechafin, idfp, tarifa, idch, placaauto, nopast, diasprorroga) VALUES (42, '2019-07-10', '2019-07-13', 8, 180, '98100407100', 'T678952', 'P98765', NULL);


--
-- TOC entry 2997 (class 0 OID 16497)
-- Dependencies: 205
-- Data for Name: estado; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.estado (id, nombre) VALUES (2, 'Alquilado');
INSERT INTO public.estado (id, nombre) VALUES (3, 'Disponible');
INSERT INTO public.estado (id, nombre) VALUES (6, 'Taller');


--
-- TOC entry 2999 (class 0 OID 16505)
-- Dependencies: 207
-- Data for Name: formapago; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.formapago (id, nombre) VALUES (8, 'Efectivo');
INSERT INTO public.formapago (id, nombre) VALUES (9, 'Cheque');
INSERT INTO public.formapago (id, nombre) VALUES (10, 'Tarjeta de Credito');
INSERT INTO public.formapago (id, nombre) VALUES (98, 'Para mi amor');


--
-- TOC entry 3001 (class 0 OID 16513)
-- Dependencies: 209
-- Data for Name: marca; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.marca (id, nombre) VALUES (1, 'Lada');
INSERT INTO public.marca (id, nombre) VALUES (4, 'Toyota');
INSERT INTO public.marca (id, nombre) VALUES (3, 'Kia');
INSERT INTO public.marca (id, nombre) VALUES (10, 'Gelly');


--
-- TOC entry 3003 (class 0 OID 16521)
-- Dependencies: 211
-- Data for Name: modelo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.modelo (id, idmarca, nombre, tarifa) VALUES (4, 1, 'Zhiguli', 60);
INSERT INTO public.modelo (id, idmarca, nombre, tarifa) VALUES (3, 1, '1600', 90);
INSERT INTO public.modelo (id, idmarca, nombre, tarifa) VALUES (2, 3, 'Picanto', 150);
INSERT INTO public.modelo (id, idmarca, nombre, tarifa) VALUES (6, 10, 'Engrand', 70);
INSERT INTO public.modelo (id, idmarca, nombre, tarifa) VALUES (5, 4, 'Yaris', 40);


--
-- TOC entry 3005 (class 0 OID 16529)
-- Dependencies: 213
-- Data for Name: pais; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pais (id, nombre) VALUES (1, 'Cuba');
INSERT INTO public.pais (id, nombre) VALUES (7, 'Estados Unidos');
INSERT INTO public.pais (id, nombre) VALUES (9, 'Rusia');
INSERT INTO public.pais (id, nombre) VALUES (8, 'España');
INSERT INTO public.pais (id, nombre) VALUES (10, 'Alemania');
INSERT INTO public.pais (id, nombre) VALUES (11, 'Italia');
INSERT INTO public.pais (id, nombre) VALUES (98, 'VeronicaDinastia');


--
-- TOC entry 3007 (class 0 OID 16545)
-- Dependencies: 215
-- Data for Name: turista; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.turista (nopasaporte, nombre, fechanacimiento, sexo, idpais) VALUES ('P98765', 'Dolores Perez', '2018-02-06', 'F', 8);
INSERT INTO public.turista (nopasaporte, nombre, fechanacimiento, sexo, idpais) VALUES ('P87654', 'Jose Fernandez', '2018-08-16', 'M', 7);
INSERT INTO public.turista (nopasaporte, nombre, fechanacimiento, sexo, idpais) VALUES ('P67543', 'Yunior Gomez', '2017-05-24', 'M', 9);
INSERT INTO public.turista (nopasaporte, nombre, fechanacimiento, sexo, idpais) VALUES ('P987654', 'Pepe Gonzalez', '2019-07-11', 'F', 1);


--
-- TOC entry 3025 (class 0 OID 0)
-- Dependencies: 203
-- Name: Contrato_idFP_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Contrato_idFP_seq"', 2, true);


--
-- TOC entry 3026 (class 0 OID 0)
-- Dependencies: 204
-- Name: Contrato_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Contrato_id_seq"', 42, true);


--
-- TOC entry 3027 (class 0 OID 0)
-- Dependencies: 206
-- Name: Estado_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Estado_id_seq"', 11, true);


--
-- TOC entry 3028 (class 0 OID 0)
-- Dependencies: 208
-- Name: FormaPago_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."FormaPago_id_seq"', 25, true);


--
-- TOC entry 3029 (class 0 OID 0)
-- Dependencies: 210
-- Name: Marca_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Marca_id_seq"', 10, true);


--
-- TOC entry 3030 (class 0 OID 0)
-- Dependencies: 212
-- Name: Modelo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Modelo_id_seq"', 7, true);


--
-- TOC entry 3031 (class 0 OID 0)
-- Dependencies: 214
-- Name: Pais_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Pais_id_seq"', 11, true);


--
-- TOC entry 3032 (class 0 OID 0)
-- Dependencies: 216
-- Name: Turista_idPais_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Turista_idPais_seq"', 1, false);


--
-- TOC entry 2860 (class 2606 OID 16593)
-- Name: auto Auto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auto
    ADD CONSTRAINT "Auto_pkey" PRIMARY KEY (placa);


--
-- TOC entry 2862 (class 2606 OID 16595)
-- Name: chofer Chofer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chofer
    ADD CONSTRAINT "Chofer_pkey" PRIMARY KEY (id);


--
-- TOC entry 2840 (class 2606 OID 16597)
-- Name: contrato Contrato_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contrato
    ADD CONSTRAINT "Contrato_pkey" PRIMARY KEY (id);


--
-- TOC entry 2842 (class 2606 OID 16599)
-- Name: estado Estado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estado
    ADD CONSTRAINT "Estado_pkey" PRIMARY KEY (id);


--
-- TOC entry 2846 (class 2606 OID 16601)
-- Name: formapago FormaPago_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formapago
    ADD CONSTRAINT "FormaPago_pkey" PRIMARY KEY (id);


--
-- TOC entry 2850 (class 2606 OID 16603)
-- Name: marca Marca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.marca
    ADD CONSTRAINT "Marca_pkey" PRIMARY KEY (id);


--
-- TOC entry 2854 (class 2606 OID 16605)
-- Name: modelo Modelo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modelo
    ADD CONSTRAINT "Modelo_pkey" PRIMARY KEY (id);


--
-- TOC entry 2856 (class 2606 OID 16607)
-- Name: pais Pais_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pais
    ADD CONSTRAINT "Pais_pkey" PRIMARY KEY (id);


--
-- TOC entry 2858 (class 2606 OID 16609)
-- Name: turista Turista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turista
    ADD CONSTRAINT "Turista_pkey" PRIMARY KEY (nopasaporte);


--
-- TOC entry 2852 (class 2606 OID 16615)
-- Name: marca marca_nombre_nombre1_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.marca
    ADD CONSTRAINT marca_nombre_nombre1_key UNIQUE (nombre) INCLUDE (nombre);


--
-- TOC entry 2844 (class 2606 OID 16617)
-- Name: estado nombre; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estado
    ADD CONSTRAINT nombre UNIQUE (nombre) INCLUDE (nombre);


--
-- TOC entry 2848 (class 2606 OID 16619)
-- Name: formapago nombreFP; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formapago
    ADD CONSTRAINT "nombreFP" UNIQUE (nombre) INCLUDE (nombre);


--
-- TOC entry 2871 (class 2620 OID 16625)
-- Name: contrato actualizarEstadoAuto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "actualizarEstadoAuto" AFTER INSERT OR UPDATE ON public.contrato FOR EACH ROW EXECUTE PROCEDURE public."alquiladoEstadoPasaAlquillado"();

ALTER TABLE public.contrato DISABLE TRIGGER "actualizarEstadoAuto";


--
-- TOC entry 2872 (class 2620 OID 16626)
-- Name: turista eliminarTuristaContrato; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "eliminarTuristaContrato" BEFORE DELETE ON public.turista FOR EACH ROW EXECUTE PROCEDURE public."eliminarTuristaContrato"();


--
-- TOC entry 2863 (class 2606 OID 16642)
-- Name: contrato idCh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contrato
    ADD CONSTRAINT "idCh" FOREIGN KEY (idch) REFERENCES public.chofer(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2869 (class 2606 OID 16647)
-- Name: auto idEstado; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auto
    ADD CONSTRAINT "idEstado" FOREIGN KEY (idestado) REFERENCES public.estado(id);


--
-- TOC entry 2864 (class 2606 OID 16652)
-- Name: contrato idFP; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contrato
    ADD CONSTRAINT "idFP" FOREIGN KEY (idfp) REFERENCES public.formapago(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2867 (class 2606 OID 16662)
-- Name: modelo idMarca; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.modelo
    ADD CONSTRAINT "idMarca" FOREIGN KEY (idmarca) REFERENCES public.marca(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2870 (class 2606 OID 16667)
-- Name: auto idModelo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auto
    ADD CONSTRAINT "idModelo" FOREIGN KEY (idmodelo) REFERENCES public.modelo(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2868 (class 2606 OID 16672)
-- Name: turista idPais; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turista
    ADD CONSTRAINT "idPais" FOREIGN KEY (idpais) REFERENCES public.pais(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2865 (class 2606 OID 16677)
-- Name: contrato noPasT; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contrato
    ADD CONSTRAINT "noPasT" FOREIGN KEY (nopast) REFERENCES public.turista(nopasaporte) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2866 (class 2606 OID 16682)
-- Name: contrato placaAuto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contrato
    ADD CONSTRAINT "placaAuto" FOREIGN KEY (placaauto) REFERENCES public.auto(placa) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2019-11-28 12:52:17

--
-- PostgreSQL database dump complete
--