import IssueItem from './IssueItem'
import './IssueList.css'

function IssueList({ issues, onDeleteIssue, onEditIssue }) {
    return (
        <ul>
            {
                issues.map(issue => (
                    <IssueItem
                        key={issue.id}
                        issue={issue}
                        onEditIssue={onEditIssue}
                        onDeleteIssue={onDeleteIssue}
                    />
                ))
            }
        </ul>
    )
}

export default IssueList