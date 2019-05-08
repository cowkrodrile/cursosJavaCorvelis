drop table ubicacion;
drop table contacto;
drop table persona;
Create table ubicacion as 
( Select id as id_persona, dir1 as direccion, col as colonia, cp, edo as estado, pais from candidato
Union
Select id,dir2,col,cp,edo,pais from candidato where dir2 is not null);
Create table contacto as
(Select id as id_persona, tel1 as telefono, 'casa' as descripcion from candidato where tel1 is not null
Union
Select id,tel2, 'trabajo' from candidato where tel2 is not null
Union
Select id,fax, 'fax' from candidato where fax is not null
Union
Select id,cel, 'celular' from candidato where cel is not null);
Create table persona as 
Select id as id_persona,email,nombre,apellidos as apellido_paterno, apellidos1 as apellido_materno, f_nace as fecha_nacimiento, sexo,edo_civil, nacion, lugar_nac from candidato;