CREATE TABLE IF NOT EXISTS public.organizations
(
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.organization_user
(
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    organization_id BIGINT,
    user_id UUID,
    organization_user_role VARCHAR(30),
    CONSTRAINT fk_organization FOREIGN KEY (organization_id) REFERENCES public.organizations(id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_organization_user_organization_id ON public.organization_user(organization_id);