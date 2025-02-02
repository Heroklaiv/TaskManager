CREATE TABLE account
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name     VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    linktg   VARCHAR(255),
    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE TABLE account_authorities
(
    account_id  BIGINT NOT NULL,
    authorities SMALLINT
);

CREATE TABLE message
(
    id_message   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text_message VARCHAR(255),
    time_message TIMESTAMP WITHOUT TIME ZONE,
    task_id      BIGINT,
    author_id    BIGINT,
    CONSTRAINT pk_message PRIMARY KEY (id_message)
);

CREATE TABLE task
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name          VARCHAR(255),
    description   VARCHAR(255),
    task_date     TIMESTAMP WITHOUT TIME ZONE,
    deadline      date,
    owner_id      BIGINT,
    performers_id BIGINT,
    work_team_id  BIGINT,
    CONSTRAINT pk_task PRIMARY KEY (id)
);

CREATE TABLE task_message
(
    task_id            BIGINT NOT NULL,
    message_id_message BIGINT NOT NULL
);

CREATE TABLE team
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name     VARCHAR(255),
    admin_id BIGINT,
    CONSTRAINT pk_team PRIMARY KEY (id)
);

CREATE TABLE team_member
(
    team_id   BIGINT NOT NULL,
    member_id BIGINT NOT NULL
);

CREATE TABLE work_place
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name         VARCHAR(255),
    entity       VARCHAR(255),
    admin_id     BIGINT,
    work_team_id BIGINT,
    tasks_id     BIGINT,
    CONSTRAINT pk_workplace PRIMARY KEY (id)
);

ALTER TABLE message
    ADD CONSTRAINT FK_MESSAGE_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES account (id);

ALTER TABLE message
    ADD CONSTRAINT FK_MESSAGE_ON_TASK FOREIGN KEY (task_id) REFERENCES task (id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_OWNER FOREIGN KEY (owner_id) REFERENCES account (id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_PERFORMERS FOREIGN KEY (performers_id) REFERENCES account (id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_WORKTEAM FOREIGN KEY (work_team_id) REFERENCES team (id);

ALTER TABLE team
    ADD CONSTRAINT FK_TEAM_ON_ADMIN FOREIGN KEY (admin_id) REFERENCES account (id);

ALTER TABLE work_place
    ADD CONSTRAINT FK_WORKPLACE_ON_ADMIN FOREIGN KEY (admin_id) REFERENCES account (id);

ALTER TABLE work_place
    ADD CONSTRAINT FK_WORKPLACE_ON_TASKS FOREIGN KEY (tasks_id) REFERENCES task (id);

ALTER TABLE work_place
    ADD CONSTRAINT FK_WORKPLACE_ON_WORKTEAM FOREIGN KEY (work_team_id) REFERENCES team (id);

ALTER TABLE account_authorities
    ADD CONSTRAINT fk_account_authorities_on_account FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE task_message
    ADD CONSTRAINT fk_tasmes_on_message FOREIGN KEY (message_id_message) REFERENCES message (id_message);

ALTER TABLE task_message
    ADD CONSTRAINT fk_tasmes_on_task FOREIGN KEY (task_id) REFERENCES task (id);

ALTER TABLE team_member
    ADD CONSTRAINT fk_teamem_on_account FOREIGN KEY (member_id) REFERENCES account (id);

ALTER TABLE team_member
    ADD CONSTRAINT fk_teamem_on_team FOREIGN KEY (team_id) REFERENCES team (id);