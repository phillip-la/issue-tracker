import './IssueItem.css'

function IssueItem({ issue, onEditIssue, onDeleteIssue }) {
  return (
    <article className="issue-card">
      <div className="issue-card-header">
        <div>
          <h3>{issue.title}</h3>
          <span className="issue-id">Issue #{issue.id}</span>
        </div>

        <span className={`priority priority-${issue.priority?.toLowerCase()}`}>
          {issue.priority}
        </span>
      </div>

      <p className="issue-description">
        {issue.description || 'No description provided.'}
      </p>

      <div className="issue-details">
        <div>
          <span>Owner</span>
          <strong>{issue.owner || 'Unassigned'}</strong>
        </div>

        <div>
          <span>Start date</span>
          <strong>{issue.startDate || 'Not set'}</strong>
        </div>

        <div>
          <span>End date</span>
          <strong>{issue.endDate || 'Not set'}</strong>
        </div>
      </div>

      <div className="issue-actions">
        <button type="button" onClick={() => onEditIssue(issue)}>
          Edit
        </button>

        <button
          type="button"
          className="delete-button"
          onClick={() => onDeleteIssue(issue.id)}
        >
          Delete
        </button>
      </div>
    </article>
  )
}

export default IssueItem