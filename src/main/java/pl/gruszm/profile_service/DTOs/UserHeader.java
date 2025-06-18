package pl.gruszm.profile_service.DTOs;

public class UserHeader
{
    private long id;
    private String email;
    private boolean hasElevatedRights;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public boolean isHasElevatedRights()
    {
        return hasElevatedRights;
    }

    public void setHasElevatedRights(boolean hasElevatedRights)
    {
        this.hasElevatedRights = hasElevatedRights;
    }
}
