# REST API

### [POST] /api/tasks

**Description:** [Creates a new task with the specified parameters]

**Inputs:**

- [taskName]: [Task name (String)]
- [description]: [Task description (String)]

**Source Data:** [Saved in the database.]

### [PATCH] /api/tasks/{id}

**Description:** [Updates an existing task with the specified identifier.]

**Inputs:**

- [taskName]: [New task name.]
- [description]: [New task description.]
- [description]: [New task start date.]
- [description]: [New task finish date.]

**Source Data:** [Saved in the database.]

### [POST] /api/tasks/{id}/start

**Description:** [Starts the execution of the task with the specified identifier.]

**Inputs:**

- [None]

**Source Data:** [Saved in the database.]

### [POST] /api/tasks/{id}/stop

**Description:** [Stops the execution of the task with the specified identifier.]

**Inputs:**

- [None]

**Source Data:** [Saved in the database.]

