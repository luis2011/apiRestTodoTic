-- insertar usuarios de prueba (clave: 12345)
insert into usuario(nombre, email, clave, rol, fecha_crea)
values('Usuario admin', 'admin@gmail.com', '$2b$10$n90tUM0lP7MJ4UNEPcUNPemWTIe3RPalsQcumfnvUyrsrZhuMfqaW', 'ADMIN', '2023-07-05 00:00:00');
insert into usuario(nombre, email, clave, rol, fecha_crea)
values('Usuario 2', 'usuario2@gmail.com', '$2b$10$n90tUM0lP7MJ4UNEPcUNPemWTIe3RPalsQcumfnvUyrsrZhuMfqaW', 'NORMAL', '2023-07-05 00:00:00');
insert into usuario(nombre, email, clave, rol, fecha_crea)
values('Usuario 3', 'usuario3@gmail.com', '$2b$10$n90tUM0lP7MJ4UNEPcUNPemWTIe3RPalsQcumfnvUyrsrZhuMfqaW', 'NORMAL', '2023-07-05 00:00:00');
insert into usuario(nombre, email, clave, rol, fecha_crea)
values('Usuario 4', 'usuario4@gmail.com', '$2b$10$n90tUM0lP7MJ4UNEPcUNPemWTIe3RPalsQcumfnvUyrsrZhuMfqaW', 'NORMAL', '2023-07-05 00:00:00');
insert into usuario(nombre, email, clave, rol, fecha_crea)
values('Usuario 5', 'usuario5@gmail.com', '$2b$10$n90tUM0lP7MJ4UNEPcUNPemWTIe3RPalsQcumfnvUyrsrZhuMfqaW', 'NORMAL', '2023-07-05 00:00:00');

-- insertar tareas de prueba
insert into tarea(titulo, estado, prioridad, fecha_crea, fecha_limite)
values('Tarea de prueba 1', 'CREADO', 2, '2023-07-05 00:00:00', '2023-07-10');
insert into tarea(titulo, estado, prioridad, fecha_crea, fecha_limite, responsable_id)
values('Tarea de prueba 2', 'ASIGNADO', 1, '2023-07-05 00:00:00', '2023-07-11', 2);
insert into tarea(titulo, estado, prioridad, fecha_crea, fecha_limite, responsable_id)
values('Tarea de prueba 3', 'ASIGNADO', 5, '2023-07-05 00:00:00', '2023-07-12', 3);
insert into tarea(titulo, estado, prioridad, fecha_crea, fecha_limite, responsable_id)
values('Tarea de prueba 4', 'FINALIZADO', 3, '2023-07-05 00:00:00', '2023-07-13', 3);
insert into tarea(titulo, estado, prioridad, fecha_crea, fecha_limite)
values('Tarea de prueba 5', 'ASIGNADO', 2, '2023-07-05 00:00:00', '2024-07-13');